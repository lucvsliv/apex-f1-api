package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.application.dto.DriverRankResponse
import com.lucvs.apex_f1_api.application.dto.TeamRankResponse

interface GetSeasonStandingUseCase {
    fun getDriverStandings(year: Int): List<DriverRankResponse>
    fun getConstructorStandings(year: Int): List<TeamRankResponse>
}
