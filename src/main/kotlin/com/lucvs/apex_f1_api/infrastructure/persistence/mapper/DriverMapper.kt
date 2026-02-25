package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.DriverEntity
import org.springframework.stereotype.Component

@Component
class DriverMapper {

    // Entity -> Domain
    fun toDomain(entity: DriverEntity): Driver {
        return Driver(
            number = entity.number,
            name = entity.name,
            country = entity.country,
            team = entity.team,
            acronym = entity.acronym
        )
    }

    // Domain -> Entity
    fun toEntity(driver: Driver, season: Int): DriverEntity {
        return DriverEntity(
            number = driver.number,
            season = season,
            name = driver.name,
            country = driver.country,
            team = driver.team,
            acronym = driver.acronym
        )
    }
}