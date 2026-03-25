package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.Driver

/**
 * 드라이버 데이터를 RDB(또는 외부)에서 조회하기 위한 Output Port 인터페이스
 */
interface LoadDriverPort {
    /**
     * 전체 Driver 목록 조회
     */
    fun loadDrivers(): List<Driver>

    /**
     * 특정 Driver ID 목록으로 다건 조회
     */
    fun loadAllByIds(ids: List<String>): List<Driver>
}