package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.domain.model.MembershipTier
import com.lucvs.apex_f1_api.domain.model.Role
import jakarta.persistence.*

@Entity
@Table(
    name = "user",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["provider", "provider_id"])
    ]
)
class UserEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    val provider: AuthProvider,

    @Column(name = "provider_id", nullable = false)
    val providerId: String,

    @Column(name = "email", nullable = true)
    val email: String?,

    @Column(name = "password", nullable = true)
    val password: String? = null,

    @Column(name = "profile_image_url", nullable = true)
    val profileImageUrl: String? = null,

    @Column(name = "nickname", nullable = false)
    val nickname: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    val role: Role,

    @Enumerated(EnumType.STRING)
    @Column(name = "tier", nullable = false)
    var tier: MembershipTier = MembershipTier.ROOKIE
)