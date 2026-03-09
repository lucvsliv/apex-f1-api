package com.lucvs.apex_f1_api.domain.model

import java.time.LocalDateTime

data class SubscriptionHistory(
    val id: Long? = null,
    val userId: Long,
    val previousTier: MembershipTier,
    val currentTier: MembershipTier,
    val action: SubscriptionAction,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
