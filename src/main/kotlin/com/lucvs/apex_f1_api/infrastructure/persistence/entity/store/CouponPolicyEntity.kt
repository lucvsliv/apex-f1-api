package com.lucvs.apex_f1_api.infrastructure.persistence.entity.store

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "coupon_policy")
class CouponPolicyEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val name: String,

    @Column(name = "discount_rate", nullable = false)
    val discountRate: Int,

    @Column(name = "total_quantity", nullable = false)
    val totalQuantity: Int,

    @Column(name = "issued_quantity", nullable = false)
    var issuedQuantity: Int = 0,

    @Column(name = "start_at", nullable = false)
    val startAt: LocalDateTime,

    @Column(name = "end_at", nullable = false)
    val endAt: LocalDateTime
) {
    fun issue() {
        require(issuedQuantity < totalQuantity) { "선착순 쿠폰이 모두 소진되었습니다." }
        this.issuedQuantity += 1
    }
}