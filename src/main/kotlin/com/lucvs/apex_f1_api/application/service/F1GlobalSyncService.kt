package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.SyncF1DataUseCase
import com.lucvs.apex_f1_api.application.service.etl.EtlProcessor
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

/**
 * 등록된 모든 ETL Processor들을 관리하고 실행하는 Orchestrator
 * - Spring Context에 등록된 모든 EtlProcessor 구현체를 주입받음
 * - 각 Processor의 order에 맞춰 순차적으로 ETL 작업을 수행
 */
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