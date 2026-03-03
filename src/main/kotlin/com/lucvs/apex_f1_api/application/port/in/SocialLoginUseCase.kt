package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.domain.model.AuthProvider

interface SocialLoginUseCase {
    fun socialLogin(provider: AuthProvider, accessToken: String): String
}