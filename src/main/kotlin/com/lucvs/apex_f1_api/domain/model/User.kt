package com.lucvs.apex_f1_api.domain.model

data class User(
    val id: Long? = null,
    val provider: AuthProvider,
    val providerId: String,
    val email: String?,
    val password: String? = null,
    val nickname: String,
    val profileImageUrl: String? = null,
    val role: Role = Role.ROLE_USER,
    val tier: MembershipTier = MembershipTier.ROOKIE    // 신규 가입자 -> ROOKIE
)