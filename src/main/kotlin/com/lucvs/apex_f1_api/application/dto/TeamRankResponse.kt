package com.lucvs.apex_f1_api.application.dto

import java.math.BigDecimal

data class TeamRankResponse(
    val position: Int?,
    val teamId: String,
    val name: String,
    val teamColor: String?,
    val points: BigDecimal
)
