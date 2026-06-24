package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.dto.SeasonCircuitResponse
import com.lucvs.apex_f1_api.application.port.`in`.GetSeasonCircuitUseCase
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.RaceRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeasonCircuitService(
    private val raceRepository: RaceRepository
) : GetSeasonCircuitUseCase {

    @Transactional(readOnly = true)
    override fun getSeasonCircuits(year: Int): List<SeasonCircuitResponse> {
        val races = raceRepository.findByYearOrderByRoundAsc(year)
        
        // Use a LinkedHashMap to preserve the order of races while keeping distinct circuits
        val distinctCircuitsMap = linkedMapOf<String, SeasonCircuitResponse>()
        
        races.forEach { race ->
            val circuit = race.circuit
            if (!distinctCircuitsMap.containsKey(circuit.id)) {
                distinctCircuitsMap[circuit.id] = SeasonCircuitResponse(
                    id = circuit.id,
                    name = circuit.name,
                    fullName = circuit.fullName,
                    city = circuit.placeName,
                    country = circuit.country.name,
                    countryCodeISO = circuit.country.alpha2Code
                )
            }
        }
        
        return distinctCircuitsMap.values.toList()
    }
}
