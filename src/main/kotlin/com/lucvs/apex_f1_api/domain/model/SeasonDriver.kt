package com.lucvs.apex_f1_api.domain.model

import java.math.BigDecimal

data class SeasonDriver(
    val year: Int,
    val driverId: String,

    // 순위 관련 (시즌 중이거나 기록이 없을 수 있으므로 Nullable)
    val positionNumber: Int? = null,
    val positionText: String? = null,

    // 최고 기록 관련
    val bestStartingGridPosition: Int? = null,
    val bestRaceResult: Int? = null,
    val bestSprintRaceResult: Int? = null,

    // 누적 스탯 (기본값 0)
    val totalRaceEntries: Int = 0,
    val totalRaceStarts: Int = 0,
    val totalRaceWins: Int = 0,
    val totalRaceLaps: Int = 0,
    val totalPodiums: Int = 0,
    val totalPoints: BigDecimal = BigDecimal.ZERO,
    val totalPolePositions: Int = 0,
    val totalFastestLaps: Int = 0,
    val totalSprintRaceStarts: Int = 0,
    val totalSprintRaceWins: Int = 0,
    val totalDriverOfTheDay: Int = 0,
    val totalGrandSlams: Int = 0
)