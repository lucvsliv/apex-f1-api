package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.SubscriptionHistory
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SubscriptionHistoryEntity
import org.springframework.stereotype.Component

@Component
class SubscriptionHistoryMapper {

    fun toDomain(entity: SubscriptionHistoryEntity): SubscriptionHistory {
        return SubscriptionHistory(
            id = entity.id,
            userId = entity.userId,
            previousTier = entity.previousTier,
            currentTier = entity.currentTier,
            action = entity.action,
            createdAt = entity.createdAt
        )
    }

    fun toEntity(domain: SubscriptionHistory): SubscriptionHistoryEntity {
        return SubscriptionHistoryEntity(
            id = domain.id,
            userId = domain.userId,
            previousTier = domain.previousTier,
            currentTier = domain.currentTier,
            action = domain.action,
            createdAt = domain.createdAt
        )
    }
}