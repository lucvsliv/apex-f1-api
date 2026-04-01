package com.lucvs.apex_f1_api.domain.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class MarketItem(
    val id: Long = 0L,
    val sellerId: Long, // 💡 User 객체 전체보다는 ID만 참조하여 결합도를 낮춥니다.
    val title: String,
    val content: String,
    val price: BigDecimal,
    val status: MarketItemStatus,
    val createdAt: LocalDateTime
)