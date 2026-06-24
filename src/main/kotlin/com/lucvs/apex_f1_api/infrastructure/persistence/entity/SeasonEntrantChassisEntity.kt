package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "season_entrant_chassis")
@IdClass(SeasonEntrantChassisId::class)
class SeasonEntrantChassisEntity(
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
    @Column(name = "chassis_id", length = 100, nullable = false)
    val chassisId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chassis_id", insertable = false, updatable = false)
    val chassis: ChassisEntity
)
