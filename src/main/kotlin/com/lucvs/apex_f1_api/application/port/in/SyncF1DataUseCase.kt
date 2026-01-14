package com.lucvs.apex_f1_api.application.port.`in`

/**
 * F1 데이터 전체 동기화 작업을 위한 Input Port
 */
interface SyncF1DataUseCase {

    /**
     * 모든 F1 데이터 동기화 수행
     */
    fun syncAll()
}