package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.IssueCouponUseCase
import com.lucvs.apex_f1_api.application.port.out.NotifyCouponResultPort
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.UserCouponEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.CouponPolicyRepository
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.UserCouponRepository
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class IssueCouponService(
    private val couponPolicyRepository: CouponPolicyRepository,
    private val userCouponRepository: UserCouponRepository,
    private val userRepository: UserRepository,
    private val notifyCouponResultPort: NotifyCouponResultPort
) : IssueCouponUseCase {

    private val log = LoggerFactory.getLogger(this::class.java)

    @Transactional
    override fun issue(couponPolicyId: Long, userId: Long) {
        // 1. 중복 발급 방지
        if (userCouponRepository.existsByUserIdAndCouponPolicyId(userId, couponPolicyId)) {
            log.warn("이미 발급된 유저입니다. UserId: {}", userId)
            return
        }

        val user = userRepository.findByIdOrNull(userId) ?: return

        // 2. Lock(Pessimistic)을 걸고 쿠폰 정책 조회
        val policy = couponPolicyRepository.findByIdForUpdate(couponPolicyId)
            ?: throw IllegalArgumentException("존재하지 않는 쿠폰 정책입니다.")

        // 3. 발급 수량 체크 및 차감 방어 로직
        try {
            policy.issue()
        } catch (e: IllegalArgumentException) {
            log.info("[!] 선착순 쿠폰 발급 실패 (수량 소진). UserId: {}, PolicyId: {}", userId, couponPolicyId)

            // 클라이언트에게 쿠폰 발급 실패 알림 전송
            notifyCouponResultPort.notifySoldOut(userId, couponPolicyId)

            return
        }

        // 4. 유저 쿠폰 저장
        val userCoupon = UserCouponEntity(
            user = user,
            couponPolicy = policy,
            isUsed = false,
            issuedAt = LocalDateTime.now()
        )
        userCouponRepository.save(userCoupon)

        // 5. 성공 알림 전송
        log.info("[*] 쿠폰 발급 성공. UserId: {}", userId)
        notifyCouponResultPort.notifySuccess(userId, couponPolicyId)
    }
}