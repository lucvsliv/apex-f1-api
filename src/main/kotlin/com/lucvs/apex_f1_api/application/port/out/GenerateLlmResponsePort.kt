package com.lucvs.apex_f1_api.application.port.out

interface GenerateLlmResponsePort {
    fun generate(prompt: String): String
}