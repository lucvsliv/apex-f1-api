package com.lucvs.apex_f1_api.infrastructure.client.toss.dto

data class TossBillingKeyResponse(
    val mId: String,
    val customerKey: String,
    val authenticatedAt: String,
    val method: String,
    val billingKey: String
)
