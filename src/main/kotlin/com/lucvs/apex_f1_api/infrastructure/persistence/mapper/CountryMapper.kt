package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Country
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.CountryEntity
import org.springframework.stereotype.Component

@Component
class CountryMapper {
    fun toDomain(entity: CountryEntity) = Country(
        id = entity.id, alpha2Code = entity.alpha2Code, alpha3Code = entity.alpha3Code,
        iocCode = entity.iocCode, name = entity.name, demonym = entity.demonym, continentId = entity.continentId
    )
    fun toEntity(domain: Country) = CountryEntity(
        id = domain.id, alpha2Code = domain.alpha2Code, alpha3Code = domain.alpha3Code,
        iocCode = domain.iocCode, name = domain.name, demonym = domain.demonym, continentId = domain.continentId
    )
}