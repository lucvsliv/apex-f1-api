package com.lucvs.apex_f1_api.infrastructure.scheduler

import com.lucvs.apex_f1_api.application.port.out.PublishCouponEventPort
import com.lucvs.apex_f1_api.infrastructure.redis.adapter.RedisCouponQueueAdapter
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CouponQueueScheduler(
    private val redisCouponQueueAdapter: RedisCouponQueueAdapter,
    private val publishCouponEventPort: PublishCouponEventPort
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    private val POP_COUNT_PER_SECOND = 10L      // 초당 소화 트래픽 양
    private val ACTIVE_COUPON_POLICY_ID = 1L    // 1번 쿠폰 정책을 폴링

    /**
     * 1초마다 실행, Redis 대기열 최상위 N명을 Kafka로 전송
     */
    @Scheduled(fixedDelay = 1000)
    fun processCouponQueue() {
        try {
            // 1. 대기열 최상단 유저 N명 추출
            val userIds = redisCouponQueueAdapter.popFirstComers(ACTIVE_COUPON_POLICY_ID, POP_COUNT_PER_SECOND)

            if (userIds.isEmpty()) return

            log.info("[*] 대기열에서 {}명의 유저 추출 완료. Kafka 전송 시작.", userIds.size)

            // 2. 꺼낸 유저들을 Kafka에 전송
            userIds.forEach { userId ->
                publishCouponEventPort.publishIssueEvent(ACTIVE_COUPON_POLICY_ID, userId)
            }

        } catch (e: Exception) {
            log.error("[!] 스케줄러 대기열 처리 중 에러 발생", e)
        }
    }
}