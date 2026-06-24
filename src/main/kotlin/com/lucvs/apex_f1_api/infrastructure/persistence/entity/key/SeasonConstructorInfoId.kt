package com.lucvs.apex_f1_api.infrastructure.persistence.entity.key

import java.io.Serializable
import java.util.Objects

class SeasonConstructorInfoId(
    var year: Int = 0,
    var constructorId: String = ""
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as SeasonConstructorInfoId
        return year == other.year && constructorId == other.constructorId
    }

    override fun hashCode(): Int {
        return Objects.hash(year, constructorId)
    }
}
