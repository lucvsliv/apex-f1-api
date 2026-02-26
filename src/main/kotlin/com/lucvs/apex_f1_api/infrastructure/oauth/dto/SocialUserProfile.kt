package com.lucvs.apex_f1_api.infrastructure.oauth.dto

import com.lucvs.apex_f1_api.domain.model.AuthProvider

data class SocialUserProfile(
    val provider: AuthProvider,
    val providerId: String,
    val email: String?,
    val nickname: String
)
