package com.lucvs.apex_f1_api.application.port.`in`

interface ChatWithAgentUseCase {
    fun chat(chatId: String, userMessage: String): String
}