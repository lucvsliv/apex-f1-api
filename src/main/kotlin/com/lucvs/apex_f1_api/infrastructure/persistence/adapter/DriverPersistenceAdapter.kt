package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import com.lucvs.apex_f1_api.application.port.out.SaveDriverPort
import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.DriverMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.respository.DriverRepository
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.pgvector.PgVectorStore
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class DriverPersistenceAdapter(
    private val driverRepository: DriverRepository,
    private val driverMapper: DriverMapper,
    private val vectorStore: PgVectorStore
) : SaveDriverPort, LoadDriverPort {

    @Transactional
    override fun save(drivers: List<Driver>, season: Int) {

        val newDocuments = mutableListOf<Document>()

        drivers.forEach { driver ->
            // 1. 중복 검사 수행
            val existingEntity = driverRepository.findByNumberAndSeason(driver.number, season)

            // 2. 저장 로직 수행
            if (existingEntity == null) {
                // 2-1. 순수 RDB 저장
                val newEntity = driverMapper.toEntity(
                    driver = driver,
                    season = season
                )
                driverRepository.save(newEntity)

                // 2-2. 통합 Vector DB용 텍스트 생성
                val descriptionText = """
                    Season: Formula 1 $season Season
                    Driver Name: ${driver.name} (${driver.acronym})
                    Team: ${driver.team}
                    Nationality: ${driver.country}
                    Number: ${driver.number}
                """.trimIndent()

                // 2-3. 메타데이터 생성
                val metadata = mapOf(
                    "domain" to "driver",
                    "season" to season,
                    "team" to driver.team,
                    "driver_number" to driver.number
                )

                // 2-4. Document 객체로 변환 후 리스트에 추가
                newDocuments.add(Document(descriptionText, metadata))

                // 로깅
                println("[+] Saved new driver to RDB: ${driver.name} ($season)")
            } else {
                println("[!] Skipped existing driver: ${driver.name}")
            }

            // 3. Vector DB에 일괄 저장 (Batch)
            if (newDocuments.isNotEmpty()) {
                vectorStore.add(newDocuments)
                println("[+] Saved ${newDocuments.size} driver documents to Vector Store.")

            }
        }
    }

    @Transactional(readOnly = true)
    override fun loadDrivers(): List<Driver> {
        return driverRepository.findAll()
            .map { driverMapper.toDomain(it) }
    }
}