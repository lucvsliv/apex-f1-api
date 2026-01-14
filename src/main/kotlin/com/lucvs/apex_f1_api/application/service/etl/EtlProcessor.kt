package com.lucvs.apex_f1_api.application.service.etl

import org.springframework.core.Ordered

/**
 * 도메인별 ETL 서비스의 인터페이스
 * - Ordered 인터페이스를 상속받아 각 ETL 서비스의 실행 순서를 제어
 * - (Meeting 데이터가 있어야 Session 데이터를 연결할 수 있기 때문)
 */
interface EtlProcessor : Ordered {
    fun process()
    fun getName(): String
}