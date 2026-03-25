package com.lucvs.apex_f1_api.domain.model

data class SeasonEntrantDriver(
    val year: Int,
    val entrantId: String,  // constructor full-name
    val constructorId: String,
    val engineManufacturerId: String,
    val driverId: String,
    val rounds: String?,
    val roundsText: String?,
    val testDriver: Boolean
)