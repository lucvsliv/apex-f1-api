package com.lucvs.apex_f1_api.infrastructure.persistence.repository.projection

import java.math.BigDecimal

interface DriverStandingProjection {
    fun getPosition(): Int?
    fun getDriverId(): String
    fun getName(): String?
    fun getTeam(): String?
    fun getTeamColor(): String?
    fun getPoints(): BigDecimal
}
