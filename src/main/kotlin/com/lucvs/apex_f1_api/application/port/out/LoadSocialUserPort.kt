package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.infrastructure.oauth.dto.SocialUserProfile

interface LoadSocialUserPort {
    // 처리 가능한 소셜인지 확인
    fun supports(provider: AuthProvider): Boolean

    // 소셜 서버에서 정보 조회
    fun loadUser(accessToken: String): SocialUserProfile
}