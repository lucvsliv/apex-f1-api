package com.lucvs.apex_f1_api.application.service.etl

import org.springframework.core.Ordered

/**
 * 모든 도메인별 ETL 서비스가 구현해야 하는 인터페이스
 * Ordered 인터페이스를 상속받아 실행 순서를 제어합니다.
 * (예: Meeting 데이터가 있어야 Session을 연결할 수 있으므로 순서 중요)
 */
interface EtlProcessor : Ordered {
    fun process()
    fun getName(): String // 로깅용 이름
}