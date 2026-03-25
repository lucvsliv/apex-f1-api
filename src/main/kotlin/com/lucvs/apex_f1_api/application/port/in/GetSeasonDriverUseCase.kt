package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.infrastructure.api.dto.SeasonDriverDetailResponse
import com.lucvs.apex_f1_api.infrastructure.api.dto.SeasonDriverSummaryResponse

interface GetSeasonDriverUseCase {
    fun getSeasonDrivers(year: Int): List<SeasonDriverSummaryResponse>
    fun getSeasonDriverDetail(year: Int, driverId: String): SeasonDriverDetailResponse
}