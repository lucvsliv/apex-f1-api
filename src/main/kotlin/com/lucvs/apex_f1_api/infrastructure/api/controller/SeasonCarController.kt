package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.dto.SeasonCarResponse
import com.lucvs.apex_f1_api.application.service.SeasonCarService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/seasons")
class SeasonCarController(
    private val seasonCarService: SeasonCarService
) {
    @GetMapping("/{year}/cars")
    fun getCarsByYear(@PathVariable year: Int): ResponseEntity<List<SeasonCarResponse>> {
        return ResponseEntity.ok(seasonCarService.getCarsByYear(year))
    }
}
