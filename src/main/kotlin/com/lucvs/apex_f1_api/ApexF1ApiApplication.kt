package com.lucvs.apex_f1_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ApexF1ApiApplication

fun main(args: Array<String>) {
	runApplication<ApexF1ApiApplication>(*args)
}
