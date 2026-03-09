package com.lucvs.apex_f1_api.infrastructure.persistence.respository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SubscriptionHistoryEntity
import org.springframework.data.jpa.repository.JpaRepository

interface SubscriptionHistoryRepository : JpaRepository<SubscriptionHistoryEntity, Long> {
}