package com.lucvs.apex_f1_api.domain.model

data class Driver(
    val number: Int,
    val name: String,
    val acronym: String,
    val team: String,
    val country: String,
    val profileImageUrl: String?,
)
