package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadSeasonEntrantDriverPort
import com.lucvs.apex_f1_api.domain.model.SeasonEntrantDriver
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.SeasonEntrantDriverMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SeasonEntrantDriverRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SeasonEntrantDriverPersistenceAdapter(
    private val seasonEntrantDriverRepository: SeasonEntrantDriverRepository,
    private val seasonEntrantDriverMapper: SeasonEntrantDriverMapper
) : LoadSeasonEntrantDriverPort {

    @Transactional(readOnly = true)
    override fun loadByYear(year: Int): List<SeasonEntrantDriver> {
        return seasonEntrantDriverRepository.findAllByYear(year)
            .map { seasonEntrantDriverMapper.toDomain(it) }
    }
}