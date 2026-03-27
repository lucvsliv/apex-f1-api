package com.lucvs.apex_f1_api.application.service.ai

import com.lucvs.apex_f1_api.application.port.`in`.ChatWithAgentUseCase
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

    override fun chat(chatId: String, userMessage: String): String {
        val systemPrompt = """
            You are Apex-AI, a specialized Formula 1 data expert.
            You have access to a PostgreSQL database containing detailed F1 historical data.
            
            ${schemaContextService.getDatabaseSchemaContext()}
            
            When a user asks a question about F1 statistics, records, or race results, follow these steps:
            1. Write a PostgreSQL SELECT query based on the schema above.
            2. Call the `executeF1SqlTool` function with your query to fetch the real data.
            3. Analyze the JSON/List result returned by the tool.
            4. Provide a natural, accurate, and concise answer to the user based strictly on the data.
            5. If the query fails, analyze the error, fix your SQL, and try calling the tool again.
        """.trimIndent()

        return chatClient.prompt()
            .system(systemPrompt)
            .user(userMessage)
            .advisors(
                MessageChatMemoryAdvisor.builder(chatMemory)
                    .conversationId(chatId)
                    .build())
            .options(
                OpenAiChatOptions.builder()
                    .toolNames("executeF1SqlTool")
                    .build()
            )
            .call()
            .content() ?: "I'm sorry, I encountered an error while processing your request."
    }
}