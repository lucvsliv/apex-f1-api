package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.domain.model.MembershipTier

interface CreateSubscriptionUseCase {
    fun subscribe(userId: Long, authKey: String, customerKey: String, targetTier: MembershipTier)
}