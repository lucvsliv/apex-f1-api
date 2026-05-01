package com.lucvs.apex_f1_api.infrastructure.redis.adapter

import com.lucvs.apex_f1_api.application.port.out.OtpPort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisOtpAdapter(
    private val redisTemplate: StringRedisTemplate
) : OtpPort {

    private val PREFIX = "otp:"

    override fun saveOtp(email: String, otp: String, expirationMinutes: Long) {
        redisTemplate.opsForValue().set(
            PREFIX + email,
            otp,
            expirationMinutes,
            TimeUnit.MINUTES
        )
    }

    override fun getOtp(email: String): String? {
        return redisTemplate.opsForValue().get(PREFIX + email)
    }

    override fun deleteOtp(email: String) {
        redisTemplate.delete(PREFIX + email)
    }
}
