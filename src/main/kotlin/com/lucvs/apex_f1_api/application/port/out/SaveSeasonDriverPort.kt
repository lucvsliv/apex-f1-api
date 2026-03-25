package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.SeasonDriver

interface SaveSeasonDriverPort {
    fun save(seasonDrivers: List<SeasonDriver>)
}