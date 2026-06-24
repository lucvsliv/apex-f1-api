package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "race_data")
@IdClass(RaceDataId::class)
class RaceDataEntity(
    @Id
    @Column(name = "race_id", nullable = false)
    val raceId: Int,

    @Id
    @Column(name = "type", length = 50, nullable = false)
    val type: String,

    @Id
    @Column(name = "position_display_order", nullable = false)
    val positionDisplayOrder: Int,

    @Column(name = "position_number")
    val positionNumber: Int? = null,

    @Column(name = "position_text", length = 4, nullable = false)
    val positionText: String,

    @Column(name = "driver_number", length = 3, nullable = false)
    val driverNumber: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    val driver: DriverEntity,

    @Column(name = "constructor_id", length = 100, nullable = false)
    val constructorId: String,

    @Column(name = "starting_grid_position_grid_penalty_positions")
    val gridPosition: Int? = null,

    @Column(name = "race_laps")
    val raceLaps: Int? = null,

    @Column(name = "race_time", length = 20)
    val raceTime: String? = null,

    @Column(name = "race_gap", length = 20)
    val raceGap: String? = null,

    @Column(name = "race_points", precision = 8, scale = 2)
    val racePoints: BigDecimal? = null,

    @Column(name = "race_reason_retired", length = 100)
    val raceReasonRetired: String? = null,

    @Column(name = "race_pit_stops")
    val racePitStops: Int? = null
)
