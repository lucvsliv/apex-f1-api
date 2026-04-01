package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Goods
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.GoodsEntity
import org.springframework.stereotype.Component

@Component
class GoodsMapper {

    fun toDomain(entity: GoodsEntity): Goods {
        return Goods(
            id            = entity.id,
            name          = entity.name,
            description   = entity.description,
            price         = entity.price,
            stockQuantity = entity.stockQuantity,
            imageUrl      = entity.imageUrl,
            createdAt     = entity.createdAt
        )
    }

    fun toEntity(domain: Goods): GoodsEntity {
        return GoodsEntity(
            id            = domain.id,
            name          = domain.name,
            description   = domain.description,
            price         = domain.price,
            stockQuantity = domain.stockQuantity,
            imageUrl      = domain.imageUrl,
            createdAt     = domain.createdAt
        )
    }
}