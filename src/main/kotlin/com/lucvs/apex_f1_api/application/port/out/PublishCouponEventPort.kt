package com.lucvs.apex_f1_api.application.port.out

interface PublishCouponEventPort {
    fun publishIssueEvent(couponPolicyId: Long, userId: Long)
}