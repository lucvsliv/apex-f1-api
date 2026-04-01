package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.UserCoupon
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.UserEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.CouponPolicyEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.UserCouponEntity
import org.springframework.stereotype.Component

@Component
class UserCouponMapper {

    fun toDomain(entity: UserCouponEntity): UserCoupon {
        return UserCoupon(
            id             = entity.id,
            userId         = entity.user.id ?: 0L,
            couponPolicyId = entity.couponPolicy.id,
            isUsed         = entity.isUsed,
            issuedAt       = entity.issuedAt,
            usedAt         = entity.usedAt
        )
    }

    fun toEntity(
        domain: UserCoupon,
        userEntity: UserEntity,
        couponPolicyEntity: CouponPolicyEntity
    ): UserCouponEntity {
        return UserCouponEntity(
            id           = domain.id,
            user         = userEntity,
            couponPolicy = couponPolicyEntity,
            isUsed       = domain.isUsed,
            issuedAt     = domain.issuedAt,
            usedAt       = domain.usedAt
        )
    }
}