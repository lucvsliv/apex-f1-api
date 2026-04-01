package com.lucvs.apex_f1_api.domain.model

import java.time.LocalDateTime

data class CouponPolicy(
    val id: Long = 0L,
    val name: String,
    val discountRate: Int,
    val totalQuantity: Int,
    val issuedQuantity: Int,
    val startAt: LocalDateTime,
    val endAt: LocalDateTime
)