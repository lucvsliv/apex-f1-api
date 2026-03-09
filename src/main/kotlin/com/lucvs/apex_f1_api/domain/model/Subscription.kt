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

    /**
     * 현재 멤버십 등급과 타켓 등급을 비교하여 Action 결정
     */
    fun determineAction(targetTier: MembershipTier): SubscriptionAction {
        return when {
            this.tier == targetTier -> SubscriptionAction.RENEW
            targetTier.dailyAiLimit > this.tier.dailyAiLimit -> SubscriptionAction.UPGRADE
            else -> SubscriptionAction.DOWNGRADE
        }
    }

    fun changeTier(targetTier: MembershipTier, newBillingKey: String): Subscription {
        return this.copy(
            tier = targetTier,
            billingKey = newBillingKey,
            nextBillingDate = LocalDateTime.now().plusMonths(1),
            status = SubscriptionStatus.ACTIVE
        )
    }
}
