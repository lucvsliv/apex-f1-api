package com.lucvs.apex_f1_api.application.port.out

interface GenerateLlmResponsePort {
    fun generateResponse(prompt: String): String
}