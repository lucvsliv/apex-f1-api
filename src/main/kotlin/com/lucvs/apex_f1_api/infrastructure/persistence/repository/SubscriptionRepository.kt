package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.domain.model.SubscriptionStatus
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SubscriptionEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionRepository : JpaRepository<SubscriptionEntity, Long> {
    fun findByUserId(userId: Long): SubscriptionEntity?
    fun findByUserIdAndStatus(userId: Long, status: SubscriptionStatus): SubscriptionEntity?
}