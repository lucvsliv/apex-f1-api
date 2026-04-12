package com.lucvs.apex_f1_api.application.port.out

interface NotifyCouponResultPort {
    fun notifySuccess(userId: Long, couponPolicyId: Long)
    fun notifySoldOut(userId: Long, couponPolicyId: Long)
}