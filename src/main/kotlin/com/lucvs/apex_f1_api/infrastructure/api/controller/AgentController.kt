package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.port.`in`.ChatWithAgentUseCase
import com.lucvs.apex_f1_api.infrastructure.api.dto.ChatRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.ChatResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api")
class AgentController(
    private val chatWithAgentUseCase: ChatWithAgentUseCase
) {

    // Health Check Endpoint
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("Apex F1 API is in good health :)")
    }

    @PostMapping("/v1/agent/chat")
    fun chatWithAgent(@RequestBody request: ChatRequest): ResponseEntity<ChatResponse> {
        val activeChatId = request.chatId ?: UUID.randomUUID().toString()
        val responseText = chatWithAgentUseCase.chat(activeChatId, request.message)

        return ResponseEntity.ok(ChatResponse(activeChatId, responseText))
    }
}