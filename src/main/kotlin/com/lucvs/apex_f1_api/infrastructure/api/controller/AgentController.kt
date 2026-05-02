package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.port.`in`.ChatCommand
import com.lucvs.apex_f1_api.application.port.`in`.ChatWithAgentUseCase
import com.lucvs.apex_f1_api.application.port.`in`.ClearChatMemoryUseCase
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
@RequestMapping("/api/v1/agent")
class AgentController(
    private val chatWithAgentUseCase: ChatWithAgentUseCase,
    private val clearChatMemoryUseCase: ClearChatMemoryUseCase
) {

    // Health Check Endpoint (Moved to consistent path or left as is if needed elsewhere)
    @GetMapping("/health")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("Apex F1 AI Agent Service is in good health :)")
    }

    @PostMapping("/chat")
    fun chatWithAgent(@RequestBody request: ChatRequest): ResponseEntity<ChatResponse> {
        val activeChatId = request.chatId ?: UUID.randomUUID().toString()
        val command = ChatCommand(chatId = activeChatId, message = request.message)
        val responseText = chatWithAgentUseCase.chat(command)

        return ResponseEntity.ok(ChatResponse(activeChatId, responseText))
    }

    @PostMapping("/clear")
    fun clearChatMemory(@RequestBody request: ChatRequest): ResponseEntity<String> {
        val activeChatId = request.chatId ?: return ResponseEntity.badRequest().body("chatId is required")
        clearChatMemoryUseCase.clear(activeChatId)
        return ResponseEntity.ok("Chat memory cleared")
    }
}