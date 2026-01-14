package com.lucvs.apex_f1_api.application.service.etl

import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import com.lucvs.apex_f1_api.domain.model.Driver
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.stereotype.Service

/**
 * 드라이버 데이터에 특화 ETL 구현체
 */
@Service
class DriverEtlService(
    private val loadDriverPort: LoadDriverPort,
    private val vectorStore: VectorStore
) : EtlProcessor {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getOrder(): Int = 1
    override fun getName(): String = "Driver ETL"

    override fun process() {
        logger.info("Starting ${getName()}...")

        // 1. Fetch
        val drivers = loadDriverPort.fetchDrivers()
        if (drivers.isEmpty()) {
            logger.warn("No drivers found. Skipping ETL.")
            return
        }

        // 2. Transform
        val documents = drivers.map { toDocument(it) }

        // 3. Load
        try {
            // TODO: 기존 데이터 중복 체크 로직 필요
            vectorStore.add(documents)
            logger.info("Successfully processed ${drivers.size} drivers.")
        } catch (e: Exception) {
            logger.error("Failed to save vectors", e)
        }
    }

    private fun toDocument(driver: Driver): Document {
        val content = """
            F1 Driver Info:
            Name: ${driver.name} (${driver.acronym}
            Number: ${driver.number}
            Team: ${driver.team}
            Country: ${driver.country}
        """.trimIndent()

        val metadata = mapOf(
            "driver_number" to driver.number,
            "team" to driver.team,
            "type" to "driver"
        )

        return Document(content, metadata)
    }
}