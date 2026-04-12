package com.lucvs.apex_f1_api.application.service.admin

import com.lucvs.apex_f1_api.infrastructure.persistence.repository.DeadLetterRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminDlqService(
    private val deadLetterRepository: DeadLetterRepository,
    private val kafkaTemplate: KafkaTemplate<String, String>
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val MAIN_TOPIC = "coupon-issue-topic"

    @Transactional
    fun retryMessage(dlqId: Long) {
        // 1. DB에서 실패 내역 조회
        val deadLetter = deadLetterRepository.findByIdOrNull(dlqId)
            ?: throw IllegalArgumentException("존재하지 않는 DLQ 항목입니다.")

        if (deadLetter.status != "PENDING") {
            throw IllegalStateException("이미 처리된 메시지입니다.")
        }

        // 2. 원본 토픽으로 재발행 (수동 복구)
        kafkaTemplate.send(MAIN_TOPIC, deadLetter.payload)
            .whenComplete { _, ex ->
                if (ex == null) {
                    log.info("[*] DLQ 메시지(ID: {})를 원본 토픽으로 재발행했습니다.", dlqId)
                }
            }

        // 3. 상태 업데이트 (재처리 완료)
        deadLetter.markAsRetried()
    }
}