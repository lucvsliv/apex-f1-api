package com.lucvs.apex_f1_api.infrastructure.redis.adapter

import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.RateLimitStatus
import com.lucvs.apex_f1_api.infrastructure.config.RedisConfig
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest
import org.springframework.context.annotation.Import
import org.springframework.data.redis.connection.RedisConnectionFactory

@DataRedisTest
@Import(RedisConfig::class, RedisRateLimitAdapter::class)
class RedisRateLimitAdapterTest {

    @Autowired
    private lateinit var rateLimitAdapter: RedisRateLimitAdapter

    @Autowired
    private lateinit var redisConnectionFactory: RedisConnectionFactory

    @AfterEach
    fun tearDown() {
        // 반복 테스트를 위하여 Redis 초기화
        redisConnectionFactory.connection.serverCommands().flushDb()
    }

    @Test
    @DisplayName("PITWALL 등급은 제한 없이 무조건 ALLOWED를 반환한다")
    fun testPitwallIsAlwaysAllowed() {
        // given
        val userId = 999L
        val clientIp = "127.0.0.1"
        val tier = MembershipTier.PITWALL

        // when & then
        for (i in 1..10) {
            val status = rateLimitAdapter.tryConsume(clientIp, userId, tier)
            assertEquals(RateLimitStatus.ALLOWED, status, "$i 번째 호출에서 막혔습니다!")
        }
    }

    @Test
    @DisplayName("유저의 일일 질문 제한 횟수를 초과하면 USER_LIMIT_EXCEEDED 상태를 반환한다")
    fun testUserLimitExceeded() {
        // given
        val userId = 1003L
        val clientIp = "192.168.1.100"
        val tier = MembershipTier.ROOKIE
        val limit = tier.dailyAiLimit

        // when 1: 허용량만큼은 모두 통과
        for (i in 1..limit) {
            val status = rateLimitAdapter.tryConsume(clientIp, userId, tier)
            assertEquals(RateLimitStatus.ALLOWED, status)
        }

        // when 2: 허용량을 딱 1번 초과
        val overLimitStatus = rateLimitAdapter.tryConsume(clientIp, userId, tier)

        // then: 유저 제한에 걸려야 함
        assertEquals(RateLimitStatus.USER_LIMIT_EXCEEDED, overLimitStatus)
    }

    @Test
    @DisplayName("IP 제한을 초과하면 유저 등급에 상관없이 IP_LIMIT_EXCEEDED 상태를 반환한다")
    fun testIpLimitExceeded() {
        // given
        val clientIp = "10.0.0.57"
        val tier = MembershipTier.ROOKIE

        // when 1: IP당 최대 허용치(30번)까지는 통과해야 함
        for (i in 1..30) {
            val fakeUserId = 3000L + i // 계속 다른 유저 ID로 요청
            val status = rateLimitAdapter.tryConsume(clientIp, fakeUserId, tier)
            assertEquals(RateLimitStatus.ALLOWED, status)
        }

        // when 2: 31번째 요청 (새로운 유저 ID로 시도해도 IP가 같음)
        val blockedStatus = rateLimitAdapter.tryConsume(clientIp, 9999L, tier)

        // then: IP 버킷이 비어있으므로 IP_LIMIT_EXCEEDED가 떠야 함
        assertEquals(RateLimitStatus.IP_LIMIT_EXCEEDED, blockedStatus)
    }
}