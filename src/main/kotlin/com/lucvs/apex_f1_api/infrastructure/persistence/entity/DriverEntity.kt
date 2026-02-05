package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.infrastructure.persistence.type.VectorType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.Type
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "drivers")
class DriverEntity(

    @Id
    @Column(name = "driver_number")
    val number: Int,

    @Column(name = "full_name")
    val name: String,

    @Column(name = "country_code")
    val country: String,

    @Column(name = "team_name")
    val team: String,

    @Column(name = "name_acronym")
    val acronym: String,

    @Column(columnDefinition = "TEXT")
    val description: String,

    @Column(columnDefinition = "vector(1536)")
    @Type(VectorType::class)
    var embedding: FloatArray? = null,
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true             // comparing memory address
        if (other !is DriverEntity) return false    // comparing class type

        return number == other.number
    }

    override fun hashCode(): Int {
        return number.hashCode()
    }
}