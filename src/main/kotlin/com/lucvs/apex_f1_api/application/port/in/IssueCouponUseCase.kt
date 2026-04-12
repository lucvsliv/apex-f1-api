package com.lucvs.apex_f1_api.application.port.`in`

interface IssueCouponUseCase {
    fun issue(couponPolicyId: Long, userId: Long)
}