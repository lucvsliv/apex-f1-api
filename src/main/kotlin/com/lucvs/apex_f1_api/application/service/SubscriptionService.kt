package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.CreateSubscriptionUseCase
import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.application.port.out.ManageUserPort
import com.lucvs.apex_f1_api.application.port.out.RequestBillingKeyPort
import com.lucvs.apex_f1_api.application.port.out.SaveSubscriptionPort
import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.Subscription
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SubscriptionService(
    private val requestBillingKeyPort: RequestBillingKeyPort,
    private val saveSubscriptionPort: SaveSubscriptionPort,
    private val loadUserPort: LoadUserPort,
    private val manageUserPort: ManageUserPort
) : CreateSubscriptionUseCase {

    @Transactional
    override fun subscribe(userId: Long, authKey: String, customerKey: String, targetTier: MembershipTier) {
        // 1. user 조회
        val user = loadUserPort.loadUserById(userId)
            ?: throw IllegalArgumentException("유저를 찾을 수 없습니다.")

        // 2. 외부 API(토스)를 통해 빌링키 발급
        val billingKey = requestBillingKeyPort.issueBillingKey(authKey, customerKey)

        // 3. 구독 도메인 생성 및 저장
        val newSubscription = Subscription.createNew(
            userId = userId,
            tier = targetTier,
            billingKey = billingKey
        )

        saveSubscriptionPort.saveSubscription(newSubscription)

        // 4. 멤버십 등급 승급 빛 저장
        val upgradedUser = user.copy(tier = targetTier)
        manageUserPort.saveUser(upgradedUser)
    }

}