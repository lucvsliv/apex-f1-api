package com.lucvs.apex_f1_api.infrastructure.persistence.entity.store

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.UserEntity
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_coupon")
class UserCouponEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    val user: UserEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_policy_id", nullable = false)
    val couponPolicy: CouponPolicyEntity,

    @Column(name = "is_used", nullable = false)
    var isUsed: Boolean = false,

    @Column(name = "issued_at", updatable = false)
    val issuedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "used_at")
    var usedAt: LocalDateTime? = null
) {
    fun use() {
        require(!isUsed) { "이미 사용 완료된 쿠폰입니다." }
        this.isUsed = true
        this.usedAt = LocalDateTime.now()
    }
}