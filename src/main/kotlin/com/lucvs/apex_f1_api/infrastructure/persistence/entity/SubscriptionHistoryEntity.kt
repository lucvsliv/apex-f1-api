package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.SubscriptionAction
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "subscription_histories")
class SubscriptionHistoryEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "previous_tier", nullable = false)
    val previousTier: MembershipTier,

    @Enumerated(EnumType.STRING)
    @Column(name = "current_tier", nullable = false)
    val currentTier: MembershipTier,

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false)
    val action: SubscriptionAction,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)