package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.SaveDriverPort
import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.DriverEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.respository.DriverRepository
import jakarta.transaction.Transactional
import org.springframework.ai.embedding.EmbeddingModel
import org.springframework.stereotype.Component

@Component
class DriverPersistenceAdapter(
    private val driverRepository: DriverRepository,
    private val embeddingModel: EmbeddingModel
) : SaveDriverPort {

    @Transactional
    override fun saveDrivers(drivers: List<Driver>) {
        drivers.forEach { driver ->
            // 1. 벡터화할 텍스트 생성
            val descriptionText = """
                Formula 1 Driver: ${driver.name} (${driver.nameAcronym}
                Team: ${driver.team}
                Country: ${driver.country}
                Number: ${driver.number}
            """.trimIndent()

            // 2. 텍스트 -> 벡터
            val vectorValues : List<Double> = embeddingModel.embed(descriptionText)
                .map { it.toDouble() }

            // Entity 매핑
            val entity = DriverEntity(
                driverNumber = driver.number,
                fullName = driver.name,
                countryCode = driver.country,
                teamName = driver.team,
                nameAcronym = driver.nameAcronym,
                description = descriptionText,
                embedding = vectorValues
            )

            // 4. 저장
            // TODO: 이미 존재하는 데이터에 대한 처리 로직 추가 필요
            driverRepository.save(entity)
        }
    }
}