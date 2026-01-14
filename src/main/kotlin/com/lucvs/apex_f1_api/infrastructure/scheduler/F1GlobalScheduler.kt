package com.lucvs.apex_f1_api.infrastructure.scheduler

import com.lucvs.apex_f1_api.application.port.`in`.SyncF1DataUseCase
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * ETL 작업을 트리거하는 스케줄러
 */
@Component
class F1GlobalScheduler(
    private val syncF1DataUseCase: SyncF1DataUseCase
) {

    @Scheduled(cron = "0 0 4 * * *")
    fun scheduledSync() {
        syncF1DataUseCase.syncAll()
    }

    // 서버 시작 시 자동 실행
    @EventListener(ApplicationReadyEvent::class)
    fun onStartup() {
        println("Server Started. Triggering initial ETL...")
        syncF1DataUseCase.syncAll()
    }
}