package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.domain.model.QueueStatus

interface JoinCouponEventUseCase {
    fun joinQueue(couponPolicyId: Long, userId: Long): QueueStatus
}

interface GetQueueStatusUseCase {
    fun getStatus(couponPolicyId: Long, userId: Long): QueueStatus
}