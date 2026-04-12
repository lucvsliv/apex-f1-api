package com.lucvs.apex_f1_api.infrastructure.kafka.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.lucvs.apex_f1_api.application.port.out.PublishCouponEventPort
import com.lucvs.apex_f1_api.infrastructure.kafka.dto.CouponIssueMessage
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaCouponProducerAdapter(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : PublishCouponEventPort {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val TOPIC_NAME = "coupon-issue-topic"

    override fun publishIssueEvent(couponPolicyId: Long, userId: Long) {
        val messageObject = CouponIssueMessage(
            couponPolicyId = couponPolicyId,
            userId = userId,
            timestamp = System.currentTimeMillis()
        )

        // 객체를 JSON String으로 변환하여 전송
        val jsonString = objectMapper.writeValueAsString(messageObject)

        kafkaTemplate.send(TOPIC_NAME, userId.toString(), jsonString)
            .whenComplete { result, ex ->
                if (ex == null) {
                    log.debug("[*] Kafka 메세지 발행 성공: {}", jsonString)
                } else {
                    log.error("[!] Kafka 메세지 발행 실패: {}", jsonString, ex)
                }
            }
    }
}