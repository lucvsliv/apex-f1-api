package com.lucvs.apex_f1_api.infrastructure.ai

import com.lucvs.apex_f1_api.application.port.out.GenerateLlmResponsePort
import org.springframework.ai.chat.client.ChatClient
import org.springframework.stereotype.Component

@Component
class SpringAiAdapter(
    chatClientBuilder: ChatClient.Builder
) : GenerateLlmResponsePort {

    private val chatClient = chatClientBuilder.build()

    override fun generateResponse(prompt: String): String {
        return chatClient.prompt()
            .user(prompt)
            .call()
            .content() ?: "답변을 생성하지 못했습니다."
    }
}