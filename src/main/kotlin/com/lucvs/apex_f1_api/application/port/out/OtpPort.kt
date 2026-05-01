package com.lucvs.apex_f1_api.application.port.out

interface OtpPort {
    fun saveOtp(email: String, otp: String, expirationMinutes: Long)
    fun getOtp(email: String): String?
    fun deleteOtp(email: String)
}
