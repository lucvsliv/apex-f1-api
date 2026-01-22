package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "drivers")
class DriverEntity(

    @Id
    @Column(name = "driver_number")
    val driverNumber: Int,

    @Column(name = "full_name")
    val fullName: String,

    @Column(name = "country_code")
    val countryCode: String,

    @Column(name = "team_name")
    val teamName: String,

    @Column(name = "name_acronym")
    val nameAcronym: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @JdbcTypeCode(SqlTypes.OTHER)
    @Column(columnDefinition = "vector(1536")
    var embedding: List<Double>? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true             // comparing memory address
        if (other !is DriverEntity) return false    // comparing class type

        return driverNumber == other.driverNumber
    }

    override fun hashCode(): Int {
        return driverNumber.hashCode()
    }
}