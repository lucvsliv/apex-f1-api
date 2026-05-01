package com.lucvs.apex_f1_api.application.port.out

interface SendEmailOtpPort {
    fun sendOtp(email: String, otp: String)
}
