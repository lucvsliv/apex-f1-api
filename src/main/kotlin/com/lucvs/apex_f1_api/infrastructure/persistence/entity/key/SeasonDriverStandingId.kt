package com.lucvs.apex_f1_api.infrastructure.persistence.entity.key

import java.io.Serializable
import java.util.Objects

class SeasonDriverStandingId(
    var year: Int = 0,
    var driverId: String = ""
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SeasonDriverStandingId
        return year == other.year && driverId == other.driverId
    }

    override fun hashCode(): Int {
        return Objects.hash(year, driverId)
    }
}
