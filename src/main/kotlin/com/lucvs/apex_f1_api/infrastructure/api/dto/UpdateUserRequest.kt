package com.lucvs.apex_f1_api.infrastructure.api.dto

data class UpdateUserRequest(
    val nickname: String? = null,
    val profileImageUrl: String? = null
)
