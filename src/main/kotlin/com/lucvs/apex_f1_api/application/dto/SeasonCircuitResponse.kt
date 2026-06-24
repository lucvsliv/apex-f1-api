package com.lucvs.apex_f1_api.application.dto

data class SeasonCircuitResponse(
    val id: String,
    val name: String,
    val fullName: String,
    val city: String,
    val country: String,
    val countryCodeISO: String
)
