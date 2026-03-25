package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*

@Entity
@Table(name = "entrant")
class EntrantEntity(
    @Id
    @Column(name = "id", length = 100, nullable = false)
    val id: String,

    @Column(name = "name", length = 100, nullable = false)
    val name: String
)