package com.lucvs.apex_f1_api.application.dto

import java.math.BigDecimal

data class DriverRankResponse(
    val position: Int?,
    val driverId: String,
    val name: String,
    val team: String,
    val teamColor: String?,
    val points: BigDecimal
)
