package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import com.lucvs.apex_f1_api.application.port.out.SaveDriverPort
import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.domain.model.DriverSearchResult
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.DriverMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.respository.DriverRepository
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DriverPersistenceAdapter(
    private val driverRepository: DriverRepository,
    private val embeddingModel: EmbeddingModel,
    private val driverMapper: DriverMapper
) : SaveDriverPort, LoadDriverPort {

    @Transactional
    override fun saveDrivers(drivers: List<Driver>, season: Int) {
        drivers.forEach { driver ->
            // 1. 중복 검사 수행
            val existingEntity = driverRepository.findByNumberAndSeason(driver.number, season)

            // 2. 데이터가 없을 때만 저장 로직 수행
            if (existingEntity == null) {

                // 2-1. 임베딩 텍스트 생성
                val descriptionText = """
                    Season": Formula 1 $season Season
                    Driver Name: ${driver.name} (${driver.acronym})
                    Team: ${driver.team}
                    Nationality: ${driver.country}
                    Number: ${driver.number}
                """.trimIndent()

                // 2-2. 임베딩 벡터 생성
                val vectorValues = embeddingModel.embed(descriptionText)

                // 3. 엔티티 변환 및 저장
                val newEntity = driverMapper.toEntity(
                    driver = driver,
                    season = season,
                    embedding = vectorValues,
                    description = descriptionText
                )

                driverRepository.save(newEntity)

                // 4. 로깅
                println("Saved new driver: ${driver.name} ($season)")
            } else {
                println("Skipped existing driver: ${driver.name}")
            }
        }
    }

    @Transactional(readOnly = true)
    override fun loadDrivers(): List<Driver> {
        return driverRepository.findAll()
            .map { driverMapper.toDomain(it) }
    }

    @Transactional(readOnly = true)
    override fun searchDrivers(query: String, limit: Int): List<DriverSearchResult> {
        // 1. User Query -> Vector
        val queryVector: FloatArray = embeddingModel.embed(query)

        // 2. DB Search (run Native Query)
        val vectorString = queryVector.contentToString()
        val results = driverRepository.searchSimilarDrivers(vectorString, limit)

        return results.map { projection ->
            driverMapper.toSearchResult(projection)
        }
    }
}