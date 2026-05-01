package com.lucvs.apex_f1_api.infrastructure.api.dto

data class SocialLoginRequest(
    val provider: String,
    val accessToken: String
)

data class AuthResponse(
    val accessToken: String
)

data class EmailOtpRequest(
    val email: String
)

data class EmailVerifyRequest(
    val email: String,
    val otp: String
)
