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
    val broadcastName: Int,

    @JsonProperty("full_name")
    val fullName: Int,

    @JsonProperty("name_acronym")
    val nameAcronym: Int,

    @JsonProperty("team_name")
    val teamName: Int,

    @JsonProperty("team_colour")
    val teamColour: Int,

    @JsonProperty("first_name")
    val firstName: Int,

    @JsonProperty("last_name")
    val lastName: Int,

    @JsonProperty("headshot_url")
    val headshotUrl: Int,

    @JsonProperty("country_code")
    val countryCode: Int
)
