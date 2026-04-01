package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.MarketItem
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.UserEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.MarketItemEntity
import org.springframework.stereotype.Component

@Component
class MarketItemMapper {

    fun toDomain(entity: MarketItemEntity): MarketItem {
        return MarketItem(
            id        = entity.id,
            sellerId  = entity.seller.id ?: 0L,
            title     = entity.title,
            content   = entity.content,
            price     = entity.price,
            status    = entity.status,
            createdAt = entity.createdAt
        )
    }

    // 연관관계 매핑을 위해 UserEntity를 파라미터로 함께 받습니다.
    fun toEntity(domain: MarketItem, sellerEntity: UserEntity): MarketItemEntity {
        return MarketItemEntity(
            id        = domain.id,
            seller    = sellerEntity,
            title     = domain.title,
            content   = domain.content,
            price     = domain.price,
            status    = domain.status,
            createdAt = domain.createdAt
        )
    }
}