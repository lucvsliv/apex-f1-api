package com.lucvs.apex_f1_api.domain.model

enum class SubscriptionAction {
    CREATE,         // 최초 구독
    UPGRADE,        // 상위 멤버십으로 변경
    DOWNGRADE,      // 하위 멤버십으로 변경
    RENEW,          // 정기 결제 갱신 성공
    CANCEL,         // 구독 해지
    PAYMENT_FAILED  // 결제 실패
}