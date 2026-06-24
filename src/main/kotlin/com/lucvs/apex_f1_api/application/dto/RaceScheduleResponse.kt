package com.lucvs.apex_f1_api.application.dto

data class SessionDto(
    val name: String,
    val time: String
)

data class RaceScheduleResponse(
    val id: Int,
    val isCurrent: Boolean,
    val round: String,
    val countryCode: String,
    val countryCodeISO: String,
    val grandPrixId: String,
    val name: String,
    val date: String,
    val circuit: String,
    val country: String,
    val city: String,
    val sessions: List<SessionDto>
)
