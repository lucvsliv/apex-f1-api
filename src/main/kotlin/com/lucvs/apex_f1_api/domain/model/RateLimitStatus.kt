package com.lucvs.apex_f1_api.domain.model

enum class RateLimitStatus {
    ALLOWED,                // 통과
    IP_LIMIT_EXCEEDED,      // IP 제한 초과
    USER_LIMIT_EXCEEDED     // 사용자 멤버십 일일 제한 초과
}
