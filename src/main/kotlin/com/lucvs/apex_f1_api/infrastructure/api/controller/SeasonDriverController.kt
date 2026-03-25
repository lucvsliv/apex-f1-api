package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.port.`in`.GetSeasonDriverUseCase
import com.lucvs.apex_f1_api.infrastructure.api.dto.SeasonDriverDetailResponse
import com.lucvs.apex_f1_api.infrastructure.api.dto.SeasonDriverSummaryResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/seasons")
class SeasonDriverController(
    private val getSeasonDriverUseCase: GetSeasonDriverUseCase
) {

    @GetMapping("/{year}/drivers")
    fun getSeasonDrivers(
        @PathVariable year: Int
    ): ResponseEntity<List<SeasonDriverSummaryResponse>> {
        val response = getSeasonDriverUseCase.getSeasonDrivers(year)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{year}/drivers/{driverId}")
    fun getSeasonDriverDetail(
        @PathVariable year: Int,
        @PathVariable driverId: String
    ): ResponseEntity<SeasonDriverDetailResponse> {
        val response = getSeasonDriverUseCase.getSeasonDriverDetail(year, driverId)
        return ResponseEntity.ok(response)
    }
}