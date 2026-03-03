package com.lucvs.apex_f1_api.domain.model

data class User(
    val id: Long? = null,
    val provider: AuthProvider,
    val providerId: String,
    val email: String?,
    val nickname: String,
    val profileImageUrl: String? = null,
    val role: Role = Role.ROLE_USER
)