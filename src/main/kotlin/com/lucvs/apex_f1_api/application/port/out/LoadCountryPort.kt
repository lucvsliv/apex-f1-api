package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.Country

interface LoadCountryPort {
    fun loadAllByIds(ids: List<String>): List<Country>
}