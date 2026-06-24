package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.dto.RaceScheduleResponse
import com.lucvs.apex_f1_api.application.service.RaceService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/races")
class RaceController(
    private val raceService: RaceService
) {
    @GetMapping("/schedules")
    fun getSchedules(@RequestParam(defaultValue = "2025") year: Int): ResponseEntity<List<RaceScheduleResponse>> {
        return ResponseEntity.ok(raceService.getSchedulesByYear(year))
    }

    @GetMapping("/{raceId}/results")
    fun getResults(@org.springframework.web.bind.annotation.PathVariable raceId: Int): ResponseEntity<List<com.lucvs.apex_f1_api.application.dto.RaceResultResponse>> {
        return ResponseEntity.ok(raceService.getRaceResults(raceId))
    }
}
