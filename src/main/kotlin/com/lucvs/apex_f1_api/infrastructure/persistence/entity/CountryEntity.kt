package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "country")
class CountryEntity(
    @Id
    @Column(name = "id", length = 100, nullable = false)
    val id: String,

    @Column(name = "alpha2_code", length = 2, nullable = false, unique = true)
    val alpha2Code: String,

    @Column(name = "alpha3_code", length = 3, nullable = false, unique = true)
    val alpha3Code: String,

    @Column(name = "ioc_code", length = 3)
    val iocCode: String? = null,

    @Column(name = "name", length = 100, nullable = false, unique = true)
    val name: String,

    @Column(name = "demonym", length = 100)
    val demonym: String? = null,

    @Column(name = "continent_id", length = 100, nullable = false)
    val continentId: String
)