package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonConstructorStandingId
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "season_constructor_standing")
@IdClass(SeasonConstructorStandingId::class)
class SeasonConstructorStandingEntity(

    @Id
    @Column(name = "year", nullable = false)
    val year: Int,

    @Id
    @Column(name = "constructor_id", length = 100, nullable = false)
    val constructorId: String,

    @Column(name = "engine_manufacturer_id", length = 100, nullable = false)
    val engineManufacturerId: String,

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
