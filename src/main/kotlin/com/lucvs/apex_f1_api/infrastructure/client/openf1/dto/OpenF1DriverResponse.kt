package com.lucvs.apex_f1_api.infrastructure.client.openf1.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenF1DriverResponse(
    @JsonProperty("meeting_key")
    val meetingKey: Int,

    @JsonProperty("session_key")
    val sessionKey: Int,

    @JsonProperty("driver_number")
    val driverNumber: Int,

    @JsonProperty("broadcast_name")
    val broadcastName: String,

    @JsonProperty("full_name")
    val fullName: String,

    @JsonProperty("name_acronym")
    val nameAcronym: String,

    @JsonProperty("team_name")
    val teamName: String?,

    @JsonProperty("team_colour")
    val teamColour: String?,

    @JsonProperty("first_name")
    val firstName: String?,

    @JsonProperty("last_name")
    val lastName: String?,

    @JsonProperty("headshot_url")
    val headshotUrl: String?,

    @JsonProperty("country_code")
    val countryCode: String?
)