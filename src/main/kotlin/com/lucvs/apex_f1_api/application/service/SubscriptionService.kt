package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.CreateSubscriptionUseCase
import com.lucvs.apex_f1_api.application.port.out.LoadSubscriptionPort
import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.application.port.out.RecordSubscriptionHistoryPort
import com.lucvs.apex_f1_api.application.port.out.RequestBillingKeyPort
import com.lucvs.apex_f1_api.application.port.out.SaveSubscriptionPort
import com.lucvs.apex_f1_api.application.port.out.SaveUserPort
import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.Subscription
import com.lucvs.apex_f1_api.domain.model.SubscriptionHistory
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SubscriptionService(
        private val requestBillingKeyPort: RequestBillingKeyPort,
        private val saveSubscriptionPort: SaveSubscriptionPort,
        private val loadSubscriptionPort: LoadSubscriptionPort,
        private val recordSubscriptionHistoryPort: RecordSubscriptionHistoryPort,
        private val loadUserPort: LoadUserPort,
        private val saveUserPort: SaveUserPort,
) : CreateSubscriptionUseCase {

    @Transactional
    override fun subscribe(
            userId: Long,
            authKey: String,
            customerKey: String,
            targetTier: MembershipTier
    ) {
        // 1. 데이터 로드 및 결제 API 호출
        val user =
                loadUserPort.loadUserById(userId)
                        ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")
        val billingKey = requestBillingKeyPort.issueBillingKey(authKey, customerKey)
        val existingSubscription = loadSubscriptionPort.loadByUserId(userId)

        // 2. 상태 갱신
        val subscription =
                existingSubscription?.changeTier(targetTier, billingKey)
                        ?: Subscription.create(userId, targetTier, billingKey)

        // 3. 로그 생성
        val history = SubscriptionHistory.create(userId, targetTier, existingSubscription)

        // 4. 저장 및 유저 멤버십 등급 변경
        saveSubscriptionPort.save(subscription)
        recordSubscriptionHistoryPort.record(history)
        saveUserPort.save(user.copy(tier = targetTier))
    }
}
