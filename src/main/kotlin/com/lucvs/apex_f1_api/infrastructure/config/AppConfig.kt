package com.lucvs.apex_f1_api.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.web.client.RestClient

@Configuration
@EnableScheduling
class AppConfig {

    @Bean
    fun restClient(builder: RestClient.Builder): RestClient {
        return builder.build()
    }
}