package com.lucvs.apex_f1_api.infrastructure.api.dto

import com.lucvs.apex_f1_api.domain.model.Country
import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.domain.model.Entrant

data class SeasonDriverSummaryResponse(
    val driverId: String,
    val name: String,
    val team: String,
    val country: String,
    val number: Int?
) {
    companion object {
        fun of(driver: Driver, country: Country, entrant: Entrant?): SeasonDriverSummaryResponse {
            return SeasonDriverSummaryResponse(
                driverId = driver.id,
                name = driver.name,
                team = entrant?.name ?: "Unknown",
                country = country.alpha2Code,
                number = driver.permanentNumber?.toIntOrNull()
            )
        }
    }
}