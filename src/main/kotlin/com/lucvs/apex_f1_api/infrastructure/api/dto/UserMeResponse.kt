package com.lucvs.apex_f1_api.infrastructure.api.dto

import com.lucvs.apex_f1_api.domain.model.User

data class UserMeResponse(
    val id: Long,
    val provider: String,
    val email: String,
    val nickname: String,
    val profileImageUrl: String?,
    val role: String
) {
    companion object {
        fun from(user: User): UserMeResponse {
            return UserMeResponse(
                id = user.id ?: throw IllegalStateException("유저 식별자(ID)가 존재하지 않습니다."),
                provider = user.provider.name,
                email = user.email ?: "이메일 미제공",
                nickname = user.nickname,
                profileImageUrl = user.profileImageUrl,
                role = user.role.name
            )
        }
    }
}