package com.lucvs.apex_f1_api.infrastructure.redis.adapter

import com.lucvs.apex_f1_api.application.port.out.ManageQueuePort
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component

@Component
class RedisCouponQueueAdapter(
    private val redisTemplate: StringRedisTemplate
) : ManageQueuePort {

    private fun getQueueKey(couponPolicyId: Long) = "event:coupon:$couponPolicyId:queue"

    override fun enqueue(couponPolicyId: Long, userId: Long, timestamp: Long): Boolean {
        val key = getQueueKey(couponPolicyId)
        val isAdded = redisTemplate.opsForZSet().add(key, userId.toString(), timestamp.toDouble())
        return isAdded == true
    }

    override fun getRank(couponPolicyId: Long, userId: Long): Long? {
        val key = getQueueKey(couponPolicyId)
        return redisTemplate.opsForZSet().rank(key, userId.toString())
    }

    override fun getTotalCount(couponPolicyId: Long): Long {
        val key = getQueueKey(couponPolicyId)
        return redisTemplate.opsForZSet().zCard(key) ?: 0L
    }

    fun popFirstComers(couponPolicyId: Long, count: Long): List<Long> {
        val key = getQueueKey(couponPolicyId)
        // ZSET에서 점수 하위 N개를 추출
        val tuples = redisTemplate.opsForZSet().popMin(key, count)

        return tuples?.mapNotNull { it.value?.toLongOrNull() } ?: emptyList()
    }
}