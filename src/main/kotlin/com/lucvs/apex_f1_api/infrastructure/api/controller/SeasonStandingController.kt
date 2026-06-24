package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.dto.DriverRankResponse
import com.lucvs.apex_f1_api.application.dto.TeamRankResponse
import com.lucvs.apex_f1_api.application.port.`in`.GetSeasonStandingUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/seasons/{year}/standings")
class SeasonStandingController(
    private val getSeasonStandingUseCase: GetSeasonStandingUseCase
) {

    @GetMapping("/drivers")
    fun getDriverStandings(
        @PathVariable year: Int
    ): ResponseEntity<List<DriverRankResponse>> {
        val standings = getSeasonStandingUseCase.getDriverStandings(year)
        return ResponseEntity.ok(standings)
    }

    @GetMapping("/constructors")
    fun getConstructorStandings(
        @PathVariable year: Int
    ): ResponseEntity<List<TeamRankResponse>> {
        val standings = getSeasonStandingUseCase.getConstructorStandings(year)
        return ResponseEntity.ok(standings)
    }
}
