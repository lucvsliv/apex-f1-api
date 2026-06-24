package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.application.dto.SeasonCircuitResponse

interface GetSeasonCircuitUseCase {
    fun getSeasonCircuits(year: Int): List<SeasonCircuitResponse>
}
