package com.lucvs.apex_f1_api.infrastructure.api.dto

import com.lucvs.apex_f1_api.domain.model.MembershipTier

data class SubscribeRequest(
    val authKey: String,
    val customerKey: String,
    val targetTier: MembershipTier
)
