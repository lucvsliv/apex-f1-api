package com.lucvs.apex_f1_api.infrastructure.oauth

import com.lucvs.apex_f1_api.application.port.out.LoadSocialUserPort
import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.infrastructure.oauth.dto.KakaoUserResponse
import com.lucvs.apex_f1_api.infrastructure.oauth.dto.SocialUserProfile
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class KakaoOAuthAdapter(
    private val restTemplate: RestTemplate = RestTemplate()
) : LoadSocialUserPort {

    override fun supports(provider: AuthProvider): Boolean {
        return provider == AuthProvider.KAKAO
    }

    override fun loadUser(accessToken: String): SocialUserProfile {
        val url = "https://kapi.kakao.com/v2/user/me"

        // 권장 헤더 설정
        val headers = HttpHeaders().apply {
            setBearerAuth(accessToken)
            set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
        }

        val request = HttpEntity<Void>(headers)
        val response = restTemplate.exchange(url, HttpMethod.GET, request, KakaoUserResponse::class.java)

        val body = response.body ?: throw IllegalArgumentException("카카오 유저 정보를 가져오지 못했습니다.")

        // 닉네임 추출: kakao_account의 profile 닉네임 우선, 없으면 properties에서 대체
        val nickname = body.kakaoAccount?.profile?.nickname
            ?: body.properties?.get("nickname")
            ?: "Unknown"

        return SocialUserProfile(
            provider = AuthProvider.KAKAO,
            providerId = body.id.toString(),
            email = body.kakaoAccount?.email,   // email 동의가 없으면 null 반환
            nickname = nickname
        )
    }

}