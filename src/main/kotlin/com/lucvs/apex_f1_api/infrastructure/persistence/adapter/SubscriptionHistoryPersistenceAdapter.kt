package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.RecordSubscriptionHistoryPort
import com.lucvs.apex_f1_api.domain.model.SubscriptionHistory
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.SubscriptionHistoryMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SubscriptionHistoryRepository
import org.springframework.stereotype.Component

@Component
class SubscriptionHistoryPersistenceAdapter(
    private val subscriptionHistoryRepository: SubscriptionHistoryRepository,
    private val subscriptionHistoryMapper: SubscriptionHistoryMapper
) : RecordSubscriptionHistoryPort {

    override fun record(history: SubscriptionHistory): SubscriptionHistory {
        val entity = subscriptionHistoryMapper.toEntity(history)
        val savedEntity = subscriptionHistoryRepository.save(entity)
        return subscriptionHistoryMapper.toDomain(savedEntity)
    }
}