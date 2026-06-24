package com.lucvs.apex_f1_api.infrastructure.persistence.repository.projection

import java.math.BigDecimal

interface ConstructorStandingProjection {
    fun getPosition(): Int?
    fun getTeamId(): String
    fun getName(): String?
    fun getTeamColor(): String?
    fun getPoints(): BigDecimal
}
