package com.lucvs.apex_f1_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
class ApexF1ApiApplication

fun main(args: Array<String>) {
    // 비동기 스레드 간 SecurityContext 전파를 위해 시스템 프로퍼티 설정 (가장 이른 시점)
    System.setProperty("spring.security.strategy", "MODE_INHERITABLETHREADLOCAL")
    runApplication<ApexF1ApiApplication>(*args)
}
