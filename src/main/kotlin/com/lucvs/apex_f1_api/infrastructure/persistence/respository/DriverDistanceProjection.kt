package com.lucvs.apex_f1_api.infrastructure.persistence.respository

interface DriverDistanceProjection {
    val number: Int           // SQL: AS number
    val name: String          // SQL: AS name
    val country: String       // SQL: AS country
    val team: String          // SQL: AS team
    val acronym: String       // SQL: AS acronym
    val distance: Double      // SQL: AS distance
}