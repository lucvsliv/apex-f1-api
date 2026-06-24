package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import java.io.Serializable

data class SeasonEntrantConstructorId(
    val year: Int = 0,
    val entrantId: String = "",
    val constructorId: String = "",
    val engineManufacturerId: String = ""
) : Serializable
