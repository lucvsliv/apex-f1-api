package com.lucvs.apex_f1_api.domain.model

data class Country(
    val id: String,
    val alpha2Code: String,
    val alpha3Code: String,
    val iocCode: String?,
    val name: String,
    val demonym: String?,
    val continentId: String
)