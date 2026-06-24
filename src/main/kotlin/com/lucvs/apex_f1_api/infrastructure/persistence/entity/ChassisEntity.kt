package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "chassis")
class ChassisEntity(
    @Id
    @Column(name = "id", length = 100, nullable = false)
    val id: String,

    @Column(name = "constructor_id", length = 100, nullable = false)
    val constructorId: String,

    @Column(name = "name", length = 100, nullable = false)
    val name: String,

    @Column(name = "full_name", length = 100, nullable = false)
    val fullName: String
)
