package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.SubscriptionStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "subscriptions")
class SubscriptionEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "tier", nullable = false)
    var tier: MembershipTier,

    @Column(name = "billing_key")
    var billingKey: String?,

    @Column(name = "next_billing_date", nullable = false)
    var nextBillingDate: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: SubscriptionStatus
)