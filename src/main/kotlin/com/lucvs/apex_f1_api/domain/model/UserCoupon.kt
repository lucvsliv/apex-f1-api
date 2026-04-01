package com.lucvs.apex_f1_api.domain.model

import java.time.LocalDateTime

data class UserCoupon(
    val id: Long = 0L,
    val userId: Long,
    val couponPolicyId: Long,
    val isUsed: Boolean,
    val issuedAt: LocalDateTime,
    val usedAt: LocalDateTime?
)