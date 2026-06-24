package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import java.io.Serializable

class RaceDataId(
    var raceId: Int = 0,
    var type: String = "",
    var positionDisplayOrder: Int = 0
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RaceDataId

        if (raceId != other.raceId) return false
        if (type != other.type) return false
        if (positionDisplayOrder != other.positionDisplayOrder) return false

        return true
    }

    override fun hashCode(): Int {
        var result = raceId
        result = 31 * result + type.hashCode()
        result = 31 * result + positionDisplayOrder
        return result
    }
}
