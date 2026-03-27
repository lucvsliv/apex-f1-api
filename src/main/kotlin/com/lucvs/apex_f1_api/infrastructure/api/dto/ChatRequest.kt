package com.lucvs.apex_f1_api.infrastructure.api.dto

data class ChatRequest(
    val chatId: String? = null,
    val message: String
)