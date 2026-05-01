package com.lucvs.apex_f1_api.application.port.`in`

interface VerifyOtpUseCase {
    fun verifyOtp(email: String, otp: String): Boolean
}
