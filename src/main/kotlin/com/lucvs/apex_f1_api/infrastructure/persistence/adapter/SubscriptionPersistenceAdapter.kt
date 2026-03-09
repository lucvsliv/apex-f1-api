package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadSubscriptionPort
import com.lucvs.apex_f1_api.application.port.out.SaveSubscriptionPort
import com.lucvs.apex_f1_api.domain.model.Subscription
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.SubscriptionMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SubscriptionRepository
import org.springframework.stereotype.Component

@Component
class SubscriptionPersistenceAdapter(
    private val subscriptionRepository: SubscriptionRepository,
    private val subscriptionMapper: SubscriptionMapper
) : LoadSubscriptionPort, SaveSubscriptionPort {

    override fun loadByUserId(userId: Long): Subscription? {
        val entity = subscriptionRepository.findByUserId(userId)
        return entity?.let { subscriptionMapper.toDomain(it) }
    }

    override fun save(subscription: Subscription): Subscription {
        val entity = subscriptionMapper.toEntity(subscription)
        val savedEntity = subscriptionRepository.save(entity)
        return subscriptionMapper.toDomain(savedEntity)
    }
}