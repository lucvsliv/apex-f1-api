package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.SeasonDriver

interface LoadSeasonDriverPort {
    fun loadSeasonDrivers(year: Int): List<SeasonDriver>
    fun loadByDriverId(driverId: String): List<SeasonDriver>
}