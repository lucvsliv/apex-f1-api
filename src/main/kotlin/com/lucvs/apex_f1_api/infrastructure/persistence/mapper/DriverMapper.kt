package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.domain.model.DriverSearchResult
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.DriverEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.respository.DriverDistanceProjection
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
    fun toEntity(driver: Driver, embedding: List<Double>, description: String): DriverEntity {
        return DriverEntity(
            number = driver.number,
            name = driver.name,
            country = driver.country,
            team = driver.team,
            acronym = driver.acronym,
            description = description,
            embedding = embedding.map { it.toFloat() }.toFloatArray()
        )
    }

    // Native Query Result -> Search Result
    fun toSearchResult(projection: DriverDistanceProjection): DriverSearchResult {
        return DriverSearchResult(
            driver = Driver(
                number = projection.number,
                name = projection.name,
                country = projection.country,
                team = projection.team,
                acronym = projection.acronym
            ),
            similarityScore = 1 - projection.distance   // (0~2) -> (-1~1)
        )
    }
}