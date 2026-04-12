package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.admin.DeadLetterEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeadLetterRepository : JpaRepository<DeadLetterEntity, Long> {

    /**
     * 특정 상태인 데드 레터 목록을 생성일 역순으로 조회
     * 어드민 페이지에서 아직 처리되지 않은 에러 건들을 모아볼 때 사용 예정
     */
    fun findAllByStatusOrderByCreatedAtDesc(status: String): List<DeadLetterEntity>

    /**
     * 특정 토픽에서 발생한 에러 건수만 카운트
     */
    fun countByTopicAndStatus(topic: String, status: String): Long
}