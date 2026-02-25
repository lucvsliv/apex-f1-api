package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(
    name = "drivers",
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["full_name", "season"])
    ]
)
class DriverEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "driver_number", nullable = false)
    val number: Int,

    @Column(name = "season", nullable = false)
    val season: Int,

    @Column(name = "team_name", nullable = false)
    val team: String,

    @Column(name = "full_name")
    val name: String,

    @Column(name = "country_code")
    val country: String,

    @Column(name = "name_acronym")
    val acronym: String,
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