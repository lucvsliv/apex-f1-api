package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.Driver

interface SaveDriverPort {
    fun save(drivers: List<Driver>, season: Int)
}