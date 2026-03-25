package com.lucvs.apex_f1_api.infrastructure.api.dto

import com.lucvs.apex_f1_api.domain.model.Country
import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.domain.model.Entrant
import com.lucvs.apex_f1_api.domain.model.SeasonDriver
import java.math.BigDecimal

data class SeasonDriverDetailResponse(
    // 선수 기본 및 소속 정보
    val driverId: String,
    val name: String,
    val team: String,
    val country: String,
    val number: Int?,

    // 시즌 상세 스탯
    val year: Int,
    val positionNumber: Int?,
    val positionText: String?,
    val bestStartingGridPosition: Int?,
    val bestRaceResult: Int?,
    val bestSprintRaceResult: Int?,
    val totalRaceEntries: Int,
    val totalRaceStarts: Int,
    val totalRaceWins: Int,
    val totalRaceLaps: Int,
    val totalPodiums: Int,
    val totalPoints: BigDecimal,
    val totalPolePositions: Int,
    val totalFastestLaps: Int,
    val totalSprintRaceStarts: Int,
    val totalSprintRaceWins: Int,
    val totalDriverOfTheDay: Int,
    val totalGrandSlams: Int
) {
    companion object {
        fun of(
            driver: Driver,
            country: Country,
            entrant: Entrant?,
            seasonDriver: SeasonDriver
        ): SeasonDriverDetailResponse {
            return SeasonDriverDetailResponse(
                driverId = driver.id,
                name = driver.fullName,
                team = entrant?.name ?: "Unknown",
                country = country.name,
                number = driver.permanentNumber?.toIntOrNull(),
                year = seasonDriver.year,
                positionNumber = seasonDriver.positionNumber,
                positionText = seasonDriver.positionText,
                bestStartingGridPosition = seasonDriver.bestStartingGridPosition,
                bestRaceResult = seasonDriver.bestRaceResult,
                bestSprintRaceResult = seasonDriver.bestSprintRaceResult,
                totalRaceEntries = seasonDriver.totalRaceEntries,
                totalRaceStarts = seasonDriver.totalRaceStarts,
                totalRaceWins = seasonDriver.totalRaceWins,
                totalRaceLaps = seasonDriver.totalRaceLaps,
                totalPodiums = seasonDriver.totalPodiums,
                totalPoints = seasonDriver.totalPoints,
                totalPolePositions = seasonDriver.totalPolePositions,
                totalFastestLaps = seasonDriver.totalFastestLaps,
                totalSprintRaceStarts = seasonDriver.totalSprintRaceStarts,
                totalSprintRaceWins = seasonDriver.totalSprintRaceWins,
                totalDriverOfTheDay = seasonDriver.totalDriverOfTheDay,
                totalGrandSlams = seasonDriver.totalGrandSlams
            )
        }
    }
}