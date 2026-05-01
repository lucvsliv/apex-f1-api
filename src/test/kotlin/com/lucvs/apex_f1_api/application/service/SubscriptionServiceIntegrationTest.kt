package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.out.RequestBillingKeyPort
import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.Role
import com.lucvs.apex_f1_api.domain.model.SubscriptionAction
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.UserEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SubscriptionHistoryRepository
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SubscriptionRepository
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.UserRepository
import io.github.bucket4j.distributed.proxy.ProxyManager
import io.lettuce.core.RedisClient
import org.assertj.core.api.Assertions.assertThat
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.mail.javamail.JavaMailSender
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional // 테스트가 끝나면 DB를 롤백하여 깨끗하게 유지합니다.
class SubscriptionServiceIntegrationTest {

    @Autowired private lateinit var subscriptionService: SubscriptionService

    @Autowired private lateinit var userRepository: UserRepository

    @Autowired private lateinit var subscriptionRepository: SubscriptionRepository

    @Autowired private lateinit var subscriptionHistoryRepository: SubscriptionHistoryRepository

    // 외부 결제 포트는 Mocking하여 실제 결제가 일어나지 않도록 방어합니다.
    @MockitoBean private lateinit var requestBillingKeyPort: RequestBillingKeyPort

    @MockitoBean private lateinit var redisClient: RedisClient

    @MockitoBean private lateinit var bucket4jProxyManager: ProxyManager<ByteArray>

    @MockitoBean private lateinit var stringRedisTemplate: StringRedisTemplate

    @MockitoBean private lateinit var javaMailSender: JavaMailSender

    @Test
    @DisplayName("최초 결제 시 유저 등급이 업그레이드되고, 구독 상태 및 이력 테이블에 데이터가 정상 저장되어야 한다.")
    fun testFirstTimeSubscription() {
        // [1] Given: ROOKIE 유저 세팅 및 Toss API 모킹
        val user =
                userRepository.save(
                        UserEntity(
                                email = "test@apex.com",
                                nickname = "Rookie",
                                tier = MembershipTier.ROOKIE,
                                provider = AuthProvider.LOCAL,
                                providerId = "dummy-local-12345",
                                role = Role.ROLE_USER
                        )
                )
        val userId = user.id!!
        val targetTier = MembershipTier.PADDOCK

        `when`(requestBillingKeyPort.issueBillingKey(any(), any())).thenReturn("dummy-billing-key")

        // [2] When: 구독 결제 로직 실행
        subscriptionService.subscribe(
                userId = userId,
                authKey = "auth-1234",
                customerKey = "cust-5678",
                targetTier = targetTier
        )

        // [3] Then: 검증 (AssertJ 사용)

        // 유저 테이블 검증
        val updatedUser = userRepository.findById(userId).get()
        assertThat(updatedUser.tier).isEqualTo(targetTier)

        // 구독 상태 테이블 검증
        val subscriptions = subscriptionRepository.findAll()
        assertThat(subscriptions).hasSize(1)
        assertThat(subscriptions.first().userId).isEqualTo(userId)
        assertThat(subscriptions.first().tier).isEqualTo(targetTier)
        assertThat(subscriptions.first().billingKey).isEqualTo("dummy-billing-key")

        // 구독 이력 테이블 검증
        val histories = subscriptionHistoryRepository.findAll()
        assertThat(histories).hasSize(1)
        assertThat(histories.first().userId).isEqualTo(userId)
        assertThat(histories.first().previousTier).isEqualTo(MembershipTier.ROOKIE)
        assertThat(histories.first().currentTier).isEqualTo(targetTier)
        assertThat(histories.first().action).isEqualTo(SubscriptionAction.CREATE)
    }
}
