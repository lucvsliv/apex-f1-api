package com.lucvs.apex_f1_api.infrastructure.persistence.entity.key

import java.io.Serializable

data class SeasonDriverId(
    var year: Int = 0,
    var driverId: String = ""
) : Serializable