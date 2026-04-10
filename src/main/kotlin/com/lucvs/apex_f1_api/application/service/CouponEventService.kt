package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.GetQueueStatusUseCase
import com.lucvs.apex_f1_api.application.port.`in`.JoinCouponEventUseCase
import com.lucvs.apex_f1_api.application.port.out.ManageQueuePort
import com.lucvs.apex_f1_api.domain.model.QueueStatus
import org.springframework.stereotype.Service

@Service
class CouponEventService(
    private val manageQueuePort: ManageQueuePort
) : JoinCouponEventUseCase, GetQueueStatusUseCase {

    override fun joinQueue(couponPolicyId: Long, userId: Long): QueueStatus {
        val timestamp = System.currentTimeMillis()
        manageQueuePort.enqueue(couponPolicyId, userId, timestamp)

        return getStatus(couponPolicyId, userId)
    }

    override fun getStatus(couponPolicyId: Long, userId: Long): QueueStatus {
        val rank = manageQueuePort.getRank(couponPolicyId, userId)
            ?: throw IllegalStateException("대기열에 존재하지 않는 유저입니다.")
        val total = manageQueuePort.getTotalCount(couponPolicyId)

        return QueueStatus(
            userId = userId,
            couponPolicyId = couponPolicyId,
            rank = rank + 1,
            totalWaiting = total
        )
    }
}