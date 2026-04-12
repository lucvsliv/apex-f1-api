package com.lucvs.apex_f1_api.infrastructure.kafka.dto

data class CouponIssueMessage(
    val couponPolicyId: Long,
    val userId: Long,
    val timestamp: Long
)