package com.lucvs.apex_f1_api.application.service.ai

import com.lucvs.apex_f1_api.application.port.`in`.ChatCommand
import com.lucvs.apex_f1_api.application.port.`in`.ChatWithAgentUseCase
import com.lucvs.apex_f1_api.infrastructure.api.dto.ChatRequest
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.openai.OpenAiChatOptions
import org.springframework.stereotype.Service

@Service
class ApexAiAgentService(
    chaClientBuilder: ChatClient.Builder,
    private val schemaContextService: SchemaContextService,
    private val chatMemory: ChatMemory
) : ChatWithAgentUseCase {

    private val chatClient = chaClientBuilder.build()

    override fun chat(command: ChatCommand): String {
        val systemPrompt = """
            You are Apex-AI, a specialized Formula 1 data and regulation expert.
            Analyze the user's intent and follow the appropriate steps based on the required tool:

            [Tool 1: executeF1SqlTool - For Statistics, Records, and Results]
            Use this for exact numbers like driver points, lap times, or historical race results.
            Database Schema:
            ${schemaContextService.getDatabaseSchemaContext()}
            
            Steps to follow:
            1. Write a PostgreSQL SELECT query based on the schema above.
            2. Call the `executeF1SqlTool` function with your query.
            3. Analyze the returned JSON/List data.
            4. Provide a natural, accurate, and concise answer based strictly on the data.
            5. If the query fails, analyze the error, fix your SQL, and try calling the tool again.

            [Tool 2: searchF1RegulationTool - For Rules, Regulations, and Historical Context]
            Use this for text-based information like VSC/SC procedures, technical guidelines, or penalty rules.
            
            Steps to follow:
            1. Formulate a precise search keyword or phrase based on the user's question.
            2. Call the `searchF1RegulationTool` function to search the Vector DB.
            3. Read and analyze the returned document chunks.
            4. Explain the rules clearly to the user, strictly based on the retrieved documents.
            5. Always cite the [Source] and (Category) provided in the document metadata.

            [General Conversation]
            If the user's input is a simple greeting or doesn't require F1 data, answer naturally in Korean without using any tools.
        """.trimIndent()

        return chatClient.prompt()
            .system(systemPrompt)
            .user(command.message)
            .advisors(
                MessageChatMemoryAdvisor.builder(chatMemory)
                    .conversationId(command.chatId)
                    .build())
            .options(
                OpenAiChatOptions.builder()
                    .toolNames("executeF1SqlTool", "searchF1RegulationTool")
                    .build()
            )
            .call()
            .content() ?: "I'm sorry, I encountered an error while processing your request."
    }
}