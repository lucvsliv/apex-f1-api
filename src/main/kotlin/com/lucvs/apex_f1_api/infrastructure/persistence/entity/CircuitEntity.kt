package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "circuit")
class CircuitEntity(
    @Id
    @Column(name = "id", length = 100, nullable = false)
    val id: String,

    @Column(name = "name", length = 100, nullable = false)
    val name: String,

    @Column(name = "full_name", length = 100, nullable = false)
    val fullName: String,

    @Column(name = "place_name", length = 100, nullable = false)
    val placeName: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    val country: CountryEntity
)
