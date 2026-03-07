package com.lucvs.apex_f1_api.domain.model

enum class SubscriptionStatus {
    ACTIVE,     // 활성 상태 (정상 결제됨)
    CANCELED,   // 구독 취소 (다음 결제일 갱신 안 함)
    FAILED,     // 결제 실패 (잔액 부족 등)
}