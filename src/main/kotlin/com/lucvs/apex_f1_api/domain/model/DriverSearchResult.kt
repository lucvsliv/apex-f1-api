package com.lucvs.apex_f1_api.domain.model

/**
 * F1 드라이버 유사도 검색 결과 도메인 모델
 */
data class DriverSearchResult(
    val driver: Driver,
    val similarityScore: Double     // 0.0 ~ 1.0
)
