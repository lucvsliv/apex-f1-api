package com.lucvs.apex_f1_api.infrastructure.kafka.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.lucvs.apex_f1_api.application.port.`in`.IssueCouponUseCase
import com.lucvs.apex_f1_api.infrastructure.kafka.dto.CouponIssueMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class CouponEventConsumer(
    private val issueCouponUseCase: IssueCouponUseCase,
    private val objectMapper: ObjectMapper,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val DLQ_TOPIC = "coupon-issue-dlq"

    @KafkaListener(
        topics = ["coupon-issue-topic"],
        groupId = "apex-f1-store-group"
    )
    fun consumeCouponIssueEvent(message: String) {
        try {
            val event = objectMapper.readValue(message, CouponIssueMessage::class.java)

            log.info("[*] Kafka 쿠폰 발급 이벤트 수신: PolicyId={}, UserId={}", event.couponPolicyId, event.userId)

            issueCouponUseCase.issue(event.couponPolicyId, event.userId)

        } catch (e: Exception) {
            log.error("[!] Kafka 이벤트 처리 중 에러 발생. DLQ로 전송합니다: {}", message, e)
            sendToDeadLetterQueue(message, e.message ?: "Unknown error")
        }
    }

    private fun sendToDeadLetterQueue(failedMessage: String, errorMessage: String) {
        // 에러 원인을 추적할 수 있도록 헤더나 메시지 자체에 에러 로그를 덧붙여서 보낼 수도 있습니다.
        kafkaTemplate.send(DLQ_TOPIC, failedMessage)
            .whenComplete { _, ex ->
                if (ex != null) {
                    log.error("[!] DLQ 전송을 실패했습니다: {}", failedMessage, ex)
                } else {
                    log.info("[*] 실패한 메시지를 DLQ({})에 안전하게 보관했습니다.", DLQ_TOPIC)
                }
            }
    }
}