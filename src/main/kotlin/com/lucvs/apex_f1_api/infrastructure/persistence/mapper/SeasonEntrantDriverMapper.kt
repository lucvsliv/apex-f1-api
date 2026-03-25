package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.SeasonEntrantDriver
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonEntrantDriverEntity
import org.springframework.stereotype.Component

@Component
class SeasonEntrantDriverMapper {

    fun toDomain(entity: SeasonEntrantDriverEntity): SeasonEntrantDriver {
        return SeasonEntrantDriver(
            year = entity.year,
            entrantId = entity.entrantId,
            constructorId = entity.constructorId,
            engineManufacturerId = entity.engineManufacturerId,
            driverId = entity.driverId,
            rounds = entity.rounds,
            roundsText = entity.roundsText,
            testDriver = entity.testDriver
        )
    }

    fun toEntity(domain: SeasonEntrantDriver): SeasonEntrantDriverEntity {
        return SeasonEntrantDriverEntity(
            year = domain.year,
            entrantId = domain.entrantId,
            constructorId = domain.constructorId,
            engineManufacturerId = domain.engineManufacturerId,
            driverId = domain.driverId,
            rounds = domain.rounds,
            roundsText = domain.roundsText,
            testDriver = domain.testDriver
        )
    }
}