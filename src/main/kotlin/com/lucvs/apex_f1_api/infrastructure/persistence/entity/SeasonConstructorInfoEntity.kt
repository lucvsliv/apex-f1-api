package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonConstructorInfoId
import jakarta.persistence.*

@Entity
@Table(name = "season_constructor_info")
@IdClass(SeasonConstructorInfoId::class)
class SeasonConstructorInfoEntity(

    @Id
    @Column(name = "year", nullable = false)
    val year: Int,

    @Id
    @Column(name = "constructor_id", length = 100, nullable = false)
    val constructorId: String,

    @Column(name = "team_color", length = 20, nullable = false)
    val teamColor: String
)
