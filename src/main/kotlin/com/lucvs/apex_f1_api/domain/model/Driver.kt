package com.lucvs.apex_f1_api.domain.model

/**
 * F1 드라이버 도메인 모델
 */
data class Driver(
    val number: Int,
    val name: String,
    val nameAcronym: String,
    val team: String,
    val country: String,
    val profileImageUrl: String?,
)
