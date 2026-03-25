package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadCountryPort
import com.lucvs.apex_f1_api.domain.model.Country
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.CountryMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.CountryRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CountryPersistenceAdapter(
    private val countryRepository: CountryRepository,
    private val countryMapper: CountryMapper
) : LoadCountryPort {

    @Transactional(readOnly = true)
    override fun loadAllByIds(ids: List<String>): List<Country> {
        return countryRepository.findAllById(ids)
            .map { countryMapper.toDomain(it) }
    }
}