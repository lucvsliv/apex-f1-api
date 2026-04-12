package com.lucvs.apex_f1_api.application.port.out

interface ManageQueuePort {
    fun enqueue(couponPolicyId: Long, userId: Long, timestamp: Long): Boolean
    fun getRank(couponPolicyId: Long, userId: Long): Long?
    fun getTotalCount(couponPolicyId: Long): Long
}