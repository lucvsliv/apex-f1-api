package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.dto.SeasonCarResponse
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SeasonEntrantChassisRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeasonCarService(
    private val seasonEntrantChassisRepository: SeasonEntrantChassisRepository
) {
    @Transactional(readOnly = true)
    fun getCarsByYear(year: Int): List<SeasonCarResponse> {
        return seasonEntrantChassisRepository.findByYear(year)
            .map {
                SeasonCarResponse(
                    constructorId = it.constructorId,
                    chassisName = it.chassis.name
                )
            }
            .sortedBy { it.constructorId }
    }
}
