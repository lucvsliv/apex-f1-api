package com.lucvs.apex_f1_api.infrastructure.persistence.entity.key

import java.io.Serializable

data class SeasonEntrantDriverId(
    var year: Int = 0,
    var entrantId: String = "",
    var constructorId: String = "",
    var engineManufacturerId: String = "",
    var driverId: String = ""
) : Serializable