package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonDriverStandingId
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "season_driver_standing")
@IdClass(SeasonDriverStandingId::class)
class SeasonDriverStandingEntity(

    @Id
    @Column(name = "year", nullable = false)
    val year: Int,

    @Id
    @Column(name = "driver_id", length = 100, nullable = false)
    val driverId: String,

    @Column(name = "position_display_order", nullable = false)
    val positionDisplayOrder: Int,

    @Column(name = "position_number")
    val positionNumber: Int? = null,

    @Column(name = "position_text", length = 100, nullable = false)
    val positionText: String,

    @Column(name = "points", precision = 8, scale = 2, nullable = false)
    val points: BigDecimal,

    @Column(name = "championship_won", nullable = false)
    val championshipWon: Boolean = false
)
