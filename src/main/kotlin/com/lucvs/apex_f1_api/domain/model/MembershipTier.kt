package com.lucvs.apex_f1_api.domain.model

enum class MembershipTier(val dailyAiLimit: Int) {
    ROOKIE(5),      // 무료 티어
    PADDOCK(50),    // 티어 1
    GARAGE(200),    // 티어 2
    PITWALL(1000)   // 티어 3
}