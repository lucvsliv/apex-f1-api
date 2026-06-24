package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.dto.SeasonTeamResponse
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SeasonEntrantConstructorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeasonTeamService(
    private val seasonEntrantConstructorRepository: SeasonEntrantConstructorRepository
) {
    @Transactional(readOnly = true)
    fun getTeamsByYear(year: Int): List<SeasonTeamResponse> {
        return seasonEntrantConstructorRepository.findByYear(year)
            .map {
                SeasonTeamResponse(
                    entrantId = it.entrantId,
                    entrantName = it.entrant.name,
                    constructorId = it.constructorId,
                    engineManufacturerId = it.engineManufacturerId
                )
            }
            .sortedBy { it.constructorId }
    }
}
