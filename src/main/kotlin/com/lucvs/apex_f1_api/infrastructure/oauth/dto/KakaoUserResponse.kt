package com.lucvs.apex_f1_api.infrastructure.oauth.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserResponse(
    val id: Long,

    @JsonProperty("connected_at")
    val connectedAt: String?,

    val properties: Map<String, String>?,

    @JsonProperty("kakao_account")
    val kakaoAccount: KakaoAccount?
) {
    data class KakaoAccount(
        @JsonProperty("profile_nickname_needs_agreement")
        val profileNicknameNeedsAgreement: Boolean?,

        val profile: Profile?,

        @JsonProperty("email_needs_agreement")
        val emailNeedsAgreement: Boolean?,

        @JsonProperty("is_email_valid")
        val isEmailValid: Boolean?,

        @JsonProperty("is_email_verified")
        val isEmailVerified: Boolean?,

        val email: String?
    )

    data class Profile(
        val nickname: String?,

        @JsonProperty("thumbnail_image_url")
        val thumbnailImageUrl: String?,

        @JsonProperty("profile_image_url")
        val profileImageUrl: String?,

        @JsonProperty("is_default_image")
        val isDefaultImage: Boolean?
    )
}