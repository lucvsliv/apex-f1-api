package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.domain.model.DriverSearchResult

/**
 * 드라이버 데이터를 외부(API, DB 등)에서 조회하기 위한 Output Port 인터페이스
 * - DIP
 */
interface LoadDriverPort {

    /**
     * OpenF1 API - Driver 목록 조회
     */
    fun loadDrivers(): List<Driver>

    /**
     * 유사도 검색 - Driver 정보 검색
     */
    fun searchDrivers(query: String, limit: Int): List<DriverSearchResult>
}