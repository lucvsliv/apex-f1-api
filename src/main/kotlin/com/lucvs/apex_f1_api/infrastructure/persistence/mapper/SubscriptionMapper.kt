package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Subscription
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SubscriptionEntity
import org.springframework.stereotype.Component

@Component
class SubscriptionMapper {

    fun toDomain(entity: SubscriptionEntity): Subscription {
        return Subscription(
            id              = entity.id,
            userId          = entity.userId,
            tier            = entity.tier,
            billingKey      = entity.billingKey,
            nextBillingDate = entity.nextBillingDate,
            status          = entity.status
        )
    }

    fun toEntity(domain: Subscription): SubscriptionEntity {
        return SubscriptionEntity(
            id              = domain.id,
            userId          = domain.userId,
            tier            = domain.tier,
            billingKey      = domain.billingKey,
            nextBillingDate = domain.nextBillingDate,
            status          = domain.status
        )
    }
}