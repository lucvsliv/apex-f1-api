package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.RateLimitStatus

interface RateLimitPort {
    /**
     * 사용자의 멤버십 등급에 따라 토큰을 1개 소비
     * @return 소비 성공 시 true (API 호출 허용), 실패 시 false (429 error)
     */
    fun tryConsume(clientIp: String, userId: Long, tier: MembershipTier): RateLimitStatus
}