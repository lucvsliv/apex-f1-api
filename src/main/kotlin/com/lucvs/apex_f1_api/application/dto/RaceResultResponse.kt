package com.lucvs.apex_f1_api.application.dto

import java.math.BigDecimal

data class RaceResultResponse(
    val position: String,
    val driverId: String,
    val driverName: String,
    val driverNumber: String,
    val constructorId: String,
    val laps: Int?,
    val timeOrGap: String?,
    val points: BigDecimal?,
    val status: String?
)
