package com.lucvs.apex_f1_api.infrastructure.kafka.consumer

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.admin.DeadLetterEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.DeadLetterRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Component

@Component
class DlqStorageConsumer(
    private val deadLetterRepository: DeadLetterRepository
) {
    private val log = LoggerFactory.getLogger(this::class.java)

    @KafkaListener(topics = ["coupon-issue-dlq"], groupId = "dlq-storage-group")
    fun storeDlqMessage(
        message: String,
        @Header(KafkaHeaders.RECEIVED_TOPIC) topic: String
    ) {
        log.info("[*] DLQ 메시지를 수집하여 DB에 보관합니다.")

        val dlqEntity = DeadLetterEntity(
            topic = topic,
            payload = message,
            errorMessage = "Kafka Consumer 처리 중 예외 발생" // 실무에선 Exception 헤더를 추출하여 저장
        )
        deadLetterRepository.save(dlqEntity)
    }
}