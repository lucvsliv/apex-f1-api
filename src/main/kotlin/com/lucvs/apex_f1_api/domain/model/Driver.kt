package com.lucvs.apex_f1_api.domain.model

import java.math.BigDecimal
import java.time.LocalDate

data class Driver(
    val id: String,
    val name: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val abbreviation: String,
    val permanentNumber: String?,
    val gender: String,
    val dateOfBirth: LocalDate,
    val dateOfDeath: LocalDate?,
    val placeOfBirth: String,

    // 국적 관련 (임시로 String ID 유지, 추후 Country 도메인 객체로 확장 가능)
    val countryOfBirthCountryId: String,
    val nationalityCountryId: String,
    val secondNationalityCountryId: String?,

    // 최고 기록
    val bestChampionshipPosition: Int?,
    val bestStartingGridPosition: Int?,
    val bestRaceResult: Int?,
    val bestSprintRaceResult: Int?,

    // 누적 스탯
    val totalChampionshipWins: Int = 0,
    val totalRaceEntries: Int = 0,
    val totalRaceStarts: Int = 0,
    val totalRaceWins: Int = 0,
    val totalRaceLaps: Int = 0,
    val totalPodiums: Int = 0,
    val totalPoints: BigDecimal = BigDecimal.ZERO,
    val totalChampionshipPoints: BigDecimal = BigDecimal.ZERO,
    val totalPolePositions: Int = 0,
    val totalFastestLaps: Int = 0,
    val totalSprintRaceStarts: Int = 0,
    val totalSprintRaceWins: Int = 0,
    val totalDriverOfTheDay: Int = 0,
    val totalGrandSlams: Int = 0
)