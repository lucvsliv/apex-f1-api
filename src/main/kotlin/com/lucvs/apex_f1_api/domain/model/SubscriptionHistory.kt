package com.lucvs.apex_f1_api.domain.model

import java.time.LocalDateTime

data class SubscriptionHistory(
    val id: Long? = null,
    val userId: Long,
    val previousTier: MembershipTier,
    val currentTier: MembershipTier,
    val action: SubscriptionAction,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        fun create(
            userId: Long,
            targetTier: MembershipTier,
            existingSubscription: Subscription?
        ): SubscriptionHistory {
            val action = existingSubscription?.determineAction(targetTier) ?: SubscriptionAction.CREATE
            val previousTier = existingSubscription?.tier ?: MembershipTier.ROOKIE

            return SubscriptionHistory(
                userId = userId,
                previousTier = previousTier,
                currentTier = targetTier,
                action = action
            )
        }
    }
}
