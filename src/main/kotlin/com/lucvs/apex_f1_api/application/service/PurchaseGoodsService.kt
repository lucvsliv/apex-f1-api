package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.PurchaseGoodsUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PurchaseGoodsService : PurchaseGoodsUseCase {
    private val log = LoggerFactory.getLogger(javaClass)

    override fun purchase(userId: Long, marketItemId: Long) {
        // [Stub] 로직 구현 (재고 확인, 포인트/결제 차감 등)
        log.info("[*] User {} purchased market item {}", userId, marketItemId)
        
        // 실제 운영 환경에서는 주문(Order) 엔티티 생성 및 결제 처리가 들어갑니다.
    }
}
