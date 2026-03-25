package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonEntrantDriverId
import jakarta.persistence.*

@Entity
@Table(name = "season_entrant_driver")
@IdClass(SeasonEntrantDriverId::class)
class SeasonEntrantDriverEntity(

    @Id
    @Column(name = "year", nullable = false)
    val year: Int,

    @Id
    @Column(name = "entrant_id", length = 100, nullable = false)
    val entrantId: String,

    @Id
    @Column(name = "constructor_id", length = 100, nullable = false)
    val constructorId: String,

    @Id
    @Column(name = "engine_manufacturer_id", length = 100, nullable = false)
    val engineManufacturerId: String,

    @Id
    @Column(name = "driver_id", length = 100, nullable = false)
    val driverId: String,

    @Column(name = "rounds", length = 100)
    val rounds: String? = null,

    @Column(name = "rounds_text", length = 100)
    val roundsText: String? = null,

    @Column(name = "test_driver", nullable = false)
    val testDriver: Boolean = false
)