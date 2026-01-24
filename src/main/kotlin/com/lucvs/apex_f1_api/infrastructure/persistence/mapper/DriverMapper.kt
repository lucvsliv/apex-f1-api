package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.domain.model.DriverSearchResult
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
    fun toEntity(driver: Driver, embedding: List<Double>, description: String): DriverEntity {
        return DriverEntity(
            number = driver.number,
            name = driver.name,
            country = driver.country,
            team = driver.team,
            acronym = driver.acronym,
            description = description,
            embedding = embedding
        )
    }

    // Native Query Result -> Search Result
    fun toSearchResult(row: Array<Any>): DriverSearchResult {
        val entity = row[0] as DriverEntity
        val distance = row[1] as Double

        // cosine distance -> similarity score
        val similarityScore = 1 - distance

        return DriverSearchResult(
            driver = toDomain(entity),
            similarityScore = similarityScore
        )
    }
}