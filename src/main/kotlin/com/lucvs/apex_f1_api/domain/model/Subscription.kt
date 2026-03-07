package com.lucvs.apex_f1_api.domain.model

import java.time.LocalDateTime

data class Subscription(
    val id: Long? = null,
    val userId: Long,
    val tier: MembershipTier,
    val billingKey: String?,
    val nextBillingDate: LocalDateTime,
    val status: SubscriptionStatus
) {
    companion object {
        fun createNew(userId: Long, tier: MembershipTier, billingKey: String): Subscription {
            return Subscription(
                userId = userId,
                tier = tier,
                billingKey = billingKey,
                nextBillingDate = LocalDateTime.now().plusMonths(1),
                status = SubscriptionStatus.ACTIVE
            )
        }
    }
}
