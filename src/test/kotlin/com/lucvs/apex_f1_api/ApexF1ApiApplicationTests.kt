package com.lucvs.apex_f1_api

import io.github.bucket4j.distributed.proxy.ProxyManager
import io.lettuce.core.RedisClient
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
@ActiveProfiles("test")
class ApexF1ApiApplicationTests {

    @MockitoBean private lateinit var redisClient: RedisClient

    @MockitoBean private lateinit var bucket4jProxyManager: ProxyManager<ByteArray>

    @MockitoBean private lateinit var stringRedisTemplate: StringRedisTemplate

    @MockitoBean private lateinit var javaMailSender: JavaMailSender

    @Test
    fun contextLoads() {
        // 이 메서드가 에러 없이 통과하면 스프링 컨텍스트가 정상적으로 로드된 것입니다!
    }
}
