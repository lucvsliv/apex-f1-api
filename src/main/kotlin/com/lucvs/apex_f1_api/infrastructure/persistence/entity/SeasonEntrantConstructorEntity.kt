package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "season_entrant_constructor")
@IdClass(SeasonEntrantConstructorId::class)
class SeasonEntrantConstructorEntity(
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrant_id", referencedColumnName = "id", insertable = false, updatable = false)
    val entrant: EntrantEntity
)
