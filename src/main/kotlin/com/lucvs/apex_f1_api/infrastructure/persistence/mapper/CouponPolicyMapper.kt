package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.CouponPolicy
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.CouponPolicyEntity
import org.springframework.stereotype.Component

@Component
class CouponPolicyMapper {

    fun toDomain(entity: CouponPolicyEntity): CouponPolicy {
        return CouponPolicy(
            id             = entity.id,
            name           = entity.name,
            discountRate   = entity.discountRate,
            totalQuantity  = entity.totalQuantity,
            issuedQuantity = entity.issuedQuantity,
            startAt        = entity.startAt,
            endAt          = entity.endAt
        )
    }

    fun toEntity(domain: CouponPolicy): CouponPolicyEntity {
        return CouponPolicyEntity(
            id             = domain.id,
            name           = domain.name,
            discountRate   = domain.discountRate,
            totalQuantity  = domain.totalQuantity,
            issuedQuantity = domain.issuedQuantity,
            startAt        = domain.startAt,
            endAt          = domain.endAt
        )
    }
}