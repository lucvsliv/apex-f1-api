package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.dto.SeasonTeamResponse
import com.lucvs.apex_f1_api.application.service.SeasonTeamService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/seasons")
class SeasonTeamController(
    private val seasonTeamService: SeasonTeamService
) {
    @GetMapping("/{year}/teams")
    fun getTeamsByYear(@PathVariable year: Int): ResponseEntity<List<SeasonTeamResponse>> {
        return ResponseEntity.ok(seasonTeamService.getTeamsByYear(year))
    }
}
