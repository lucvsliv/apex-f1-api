package com.lucvs.apex_f1_api.application.service.etl

import com.lucvs.apex_f1_api.application.port.`in`.SyncF1DataUseCase
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class F1GlobalSyncService(
    private val processors: List<EtlProcessor>
) : SyncF1DataUseCase {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun syncAll() {
        logger.info("[Global Sync] Starting all ETL processes...")

        processors.sortedBy { it.order }.forEach { processor ->
            try {
                logger.info("Executing: ${processor.getName()}")
                processor.process()
            } catch (e: Exception) {
                logger.error("Error in ${processor.getName()}: ${e.message}", e)
            }
        }

        logger.info("[Global Sync] All processes finished.")
    }
}