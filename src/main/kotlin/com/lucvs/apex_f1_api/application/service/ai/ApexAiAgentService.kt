package com.lucvs.apex_f1_api.application.service.ai

import com.lucvs.apex_f1_api.application.port.`in`.ChatCommand
import com.lucvs.apex_f1_api.application.port.`in`.ChatWithAgentUseCase
import com.lucvs.apex_f1_api.application.port.`in`.ClearChatMemoryUseCase
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
) : ChatWithAgentUseCase, ClearChatMemoryUseCase {

    private val chatClient = chaClientBuilder.build()

    override fun chat(command: ChatCommand): String {
        val systemPrompt =
                """
            You are Apex-AI, a specialized Formula 1 data expert and personal assistant.
            Analyze the user's intent and follow the appropriate steps based on the required tool:

            [Category 1: Information & Analysis]
            - Use `executeF1SqlTool` for statistics, records, and race results.
            - Use `searchF1RegulationTool` for rules, regulations, and historical context.

            [Category 2: Action Execution]
            - 게시글 작성(Post Creation) 요청 시 반드시 다음 순서를 지키세요:
              1. 사용자가 게시글을 쓰고 싶다고 하면, "제목은 어떻게 할까요?"라고 먼저 물어보세요.
              2. 사용자가 제목을 말하면, "내용은 어떻게 할까요?"라고 물어보세요.
              3. 제목과 내용이 모두 확보되면, 아래의 JSON 패턴을 사용하여 초안 카드를 보여주세요:
                 `:::post_draft {"title": "추출된 제목", "content": "추출된 내용", "category": "Discussion"} :::`
              4. 만약 사용자가 처음부터 제목과 내용을 모두 말했다면 바로 초안 카드를 보여줘도 됩니다.
            
            - Use `joinMembershipTool` when the user says "가입해줘", "멤버십 신청해줘". (Tiers: ROOKIE, CHAMPION, LEGEND)
            - Use `createPostTool` ONLY when the user explicitly says "당장 게시글 올려줘" or "확인했으니 등록해줘".
            - Use `purchaseGoodsTool` for direct purchase. To suggest a product, use:
              `:::store_action {"productName": "이름", "price": 1000, "imageUrl": "URL", "quantity": 1} :::`
            
            Steps for Action:
            1. 정보가 부족하면(제목, 내용 등) 반드시 단계별로 사용자에게 물어보세요.
            2. `:::action_name {...} :::` 패턴은 사용자에게 미리보기를 보여줄 때 사용합니다.
            3. 최종 확인이 되었거나 직접적인 실행 요청이 있을 때만 Tool을 호출하세요.
            4. 모든 응답은 친절한 한국어로 답변하세요.

            [General Conversation]
            If the user's input is a simple greeting or doesn't require tools, answer naturally in Korean.
        """.trimIndent()

        return chatClient
                .prompt()
                .system(systemPrompt)
                .user(command.message)
                .advisors(
                        MessageChatMemoryAdvisor.builder(chatMemory)
                                .conversationId(command.chatId)
                                .build()
                )
                .options(
                        OpenAiChatOptions.builder()
                                .toolNames(
                                        "executeF1SqlTool",
                                        "searchF1RegulationTool",
                                        "createPostTool",
                                        "joinMembershipTool",
                                        "purchaseGoodsTool"
                                )
                                .build()
                )
                .call()
                .content()
                ?: "죄송합니다. 요청을 처리하는 중에 오류가 발생했습니다."
    }

    override fun clear(chatId: String) {
        chatMemory.clear(chatId)
    }
}
