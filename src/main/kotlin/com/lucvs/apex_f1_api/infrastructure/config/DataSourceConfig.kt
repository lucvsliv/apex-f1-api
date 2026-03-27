package com.lucvs.apex_f1_api.infrastructure.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.core.JdbcTemplate
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    // 1. 메인 DataSource (JPA가 기본으로 사용)
    @Primary
    @Bean(name = ["mainDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.main")
    fun mainDataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    @Primary
    @Bean(name = ["jdbcTemplate", "mainJdbcTemplate"])
    fun mainJdbcTemplate(
        @Qualifier("mainDataSource") dataSource: DataSource
    ): JdbcTemplate {
        return JdbcTemplate(dataSource)
    }

    // 2. ApexAI 전용 Read-Only DataSource
    @Bean(name = ["apexAiReadOnlyDataSource"])
    @ConfigurationProperties(prefix = "spring.datasource.apexai-readonly")
    fun apexAiReadOnlyDataSource(): DataSource {
        return DataSourceBuilder.create().type(HikariDataSource::class.java).build()
    }

    // 3. ApexAI 전용 JdbcTemplate (Apex-AI가 생성한 동적 SQL 실행용)
    @Bean(name = ["apexAiJdbcTemplate"])
    fun apexAiJdbcTemplate(
        @Qualifier("apexAiReadOnlyDataSource") dataSource: DataSource
    ): JdbcTemplate {
        val jdbcTemplate = JdbcTemplate(dataSource)
        // 보안 및 성능 방어벽 (타임아웃 5초, 최대 100행 제한)
        jdbcTemplate.queryTimeout = 5
        jdbcTemplate.maxRows = 100
        return jdbcTemplate
    }
}