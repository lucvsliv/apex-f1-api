package com.lucvs.apex_f1_api.infrastructure.config

import io.github.bucket4j.distributed.proxy.ProxyManager
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager
import io.lettuce.core.RedisClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host:localhost}") private val host: String,
    @Value("\${spring.data.redis.port:6379}") private val port: Int
) {

    // 1. lettuce redis client 생성
    @Bean
    fun redisClient(): RedisClient {
        return RedisClient.create("redis://$host:$port")
    }

    // 2. bucket4j-redis 연결 bean 등록
    @Bean
    fun bucket4jProxyManager(redisClient: RedisClient): ProxyManager<ByteArray> {
        return LettuceBasedProxyManager.builderFor(redisClient).build()
    }
}