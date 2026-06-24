package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.dto.SeasonCircuitResponse
import com.lucvs.apex_f1_api.application.port.`in`.GetSeasonCircuitUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/seasons")
class SeasonCircuitController(
    private val getSeasonCircuitUseCase: GetSeasonCircuitUseCase
) {

    @GetMapping("/{year}/circuits")
    fun getSeasonCircuits(
        @PathVariable year: Int
    ): ResponseEntity<List<SeasonCircuitResponse>> {
        val response = getSeasonCircuitUseCase.getSeasonCircuits(year)
        return ResponseEntity.ok(response)
    }
}
