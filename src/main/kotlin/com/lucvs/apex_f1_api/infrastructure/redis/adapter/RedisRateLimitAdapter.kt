package com.lucvs.apex_f1_api.infrastructure.redis.adapter

import com.lucvs.apex_f1_api.application.port.out.RateLimitPort
import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.RateLimitStatus
import io.github.bucket4j.Bandwidth
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.distributed.proxy.ProxyManager
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisRateLimitAdapter(
    private val proxyManager: ProxyManager<ByteArray>
) : RateLimitPort {

    // 사용자 기반 제한 (limit/hour)
    private val userBucketConfigurations = MembershipTier.entries.associateWith { tier ->
        BucketConfiguration.builder()
            .addLimit(
                Bandwidth.builder()
                    .capacity(tier.dailyAiLimit.toLong())
                    .refillIntervally(tier.dailyAiLimit.toLong(), Duration.ofHours(24))
                    .build()
            )
            .build()
    }

    // IP 기반 제한 (30/min)
    private val ipBucketConfiguration = BucketConfiguration.builder()
            .addLimit(
                Bandwidth.builder()
                    .capacity(50)
                    .refillIntervally(30, Duration.ofMinutes(1))
                    .build()
            )
            .build()

    override fun tryConsume(clientIp: String, userId: Long, tier: MembershipTier): RateLimitStatus {
        if (tier == MembershipTier.PITWALL) { return RateLimitStatus.ALLOWED }

        // 1. IP 버킷 검사
        val ipKey = "rate_limit:ai_agent_ip:$clientIp".toByteArray()
        val ipBucket = proxyManager.builder().build(ipKey) { ipBucketConfiguration }

        if (!ipBucket.tryConsume(1)) { return RateLimitStatus.IP_LIMIT_EXCEEDED }   // 해당 IP 분당 사용량 초과

        // 2. 사용자 버킷 검사
        val userKey = "rate_limit:ai_agent_user:$userId".toByteArray()
        val userConfiguration = userBucketConfigurations.getValue(tier)
        val userBucket = proxyManager.builder().build(userKey) { userConfiguration }

        if (!userBucket.tryConsume(1)) { return RateLimitStatus.USER_LIMIT_EXCEEDED }

        return RateLimitStatus.ALLOWED
    }
}