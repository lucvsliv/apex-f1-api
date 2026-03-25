package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.SeasonEntrantDriver

interface LoadSeasonEntrantDriverPort {
    fun loadByYear(year: Int): List<SeasonEntrantDriver>
}