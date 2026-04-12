package com.lucvs.apex_f1_api.domain.model

data class QueueStatus(
    val userId: Long,
    val couponPolicyId: Long,
    val rank: Long,
    val totalWaiting: Long
)