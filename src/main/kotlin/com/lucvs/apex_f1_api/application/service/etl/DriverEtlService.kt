package com.lucvs.apex_f1_api.application.service.etl

import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import com.lucvs.apex_f1_api.application.port.out.SaveDriverPort
import com.lucvs.apex_f1_api.domain.model.Driver
import org.slf4j.LoggerFactory
import org.springframework.ai.document.Document
import org.springframework.ai.vectorstore.VectorStore
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

/**
 * 드라이버 데이터 특화 ETL 구현체
 */
@Service
class DriverEtlService(
    @Qualifier("openF1DriverAdapter") private val loadDriverPort: LoadDriverPort,
    private val saveDriverPort: SaveDriverPort,
) : EtlProcessor {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun getOrder(): Int = 1
    override fun getName(): String = "Driver ETL"

    override fun process() {
        logger.info("Starting ${getName()}...")

        // 1. Fetch
        val drivers = loadDriverPort.loadDrivers()
        if (drivers.isEmpty()) {
            logger.warn("No drivers found. Skipping ETL.")
            return
        }

        // 2. Transform & Load
        try {
            saveDriverPort.save(drivers, 2026)
            logger.info("Successfully processed and saved ${drivers.size} drivers.")
        } catch (e: Exception) {
            logger.error("Failed to save drivers", e)
        }
    }
}