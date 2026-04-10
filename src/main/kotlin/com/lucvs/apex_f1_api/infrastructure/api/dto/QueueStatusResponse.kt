package com.lucvs.apex_f1_api.infrastructure.api.dto

import com.lucvs.apex_f1_api.domain.model.QueueStatus

data class QueueStatusResponse(
    val userId: Long,
    val couponPolicyId: Long,
    val rank: Long,
    val totalWaiting: Long
) {
    companion object {
        fun from(domain: QueueStatus) = QueueStatusResponse(
            userId = domain.userId,
            couponPolicyId = domain.couponPolicyId,
            rank = domain.rank,
            totalWaiting = domain.totalWaiting
        )
    }
}