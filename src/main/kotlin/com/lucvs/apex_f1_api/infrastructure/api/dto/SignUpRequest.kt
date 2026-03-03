package com.lucvs.apex_f1_api.infrastructure.api.dto

data class SignUpRequest(
    val email: String,
    val nickname: String,
    val password: String,
    val profileImageUrl: String?
)
