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
    override fun saveDrivers(drivers: List<Driver>) {
        drivers.forEach { driver ->
            // 1. 벡터화할 텍스트 생성
            val descriptionText = """
                Formula 1 Driver: ${driver.name} (${driver.acronym}
                Team: ${driver.team}
                Country: ${driver.country}
                Number: ${driver.number}
            """.trimIndent()

            // 2. 텍스트 -> 벡터
            val vectorValues : List<Double> = embeddingModel.embed(descriptionText)
                .map { it.toDouble() }

            // 3. Entity 매핑
            val entity = driverMapper.toEntity(driver, vectorValues, descriptionText)

            // 4. 저장
            // TODO: 이미 존재하는 데이터에 대한 처리 로직 추가 필요
            driverRepository.save(entity)
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