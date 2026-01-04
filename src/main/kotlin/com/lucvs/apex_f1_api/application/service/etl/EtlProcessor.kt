package com.lucvs.apex_f1_api.application.service.etl

import org.springframework.core.Ordered

interface EtlProcessor : Ordered {
    fun process()
    fun getName(): String // 로깅용 이름
}