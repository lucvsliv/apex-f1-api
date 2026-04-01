package com.lucvs.apex_f1_api.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Goods(
    val id: Long = 0L,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val stockQuantity: Int,
    val imageUrl: String?,
    val createdAt: LocalDateTime
)