package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.UserCouponEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserCouponRepository : JpaRepository<UserCouponEntity, Long> {
    fun countByCouponPolicyId(couponPolicyId: Long): Long
    fun existsByUserIdAndCouponPolicyId(userId: Long, couponPolicyId: Long): Boolean
}