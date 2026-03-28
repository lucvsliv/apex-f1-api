package com.lucvs.apex_f1_api.infrastructure.ai.tool

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import org.springframework.jdbc.core.JdbcTemplate
import java.util.function.Function

@Configuration
class F1SqlToolProvider(
    @Qualifier("apexAiJdbcTemplate") private val apexAiJdbcTemplate: JdbcTemplate
) {
    private val log = LoggerFactory.getLogger(javaClass)

    data class SqlRequest(val query: String)
    data class SqlResponse(val result: String)

    @Bean
    @Description("Executes a Read-Only PostgreSQL query to fetch Formula 1 data. Input must be a valid SELECT query.")
    fun executeF1SqlTool(): Function<SqlRequest, SqlResponse> {
        return Function { request ->
            val sql = request.query.trim()
            log.info("[*] Apex-AI generated SQL: {}", sql)

            // secondary barrier for DB integrity
            val upperSql = sql.uppercase()
            if (upperSql.contains("INSERT ") || upperSql.contains("UPDATE ") ||
                upperSql.contains("DELETE ") || upperSql.contains("DROP ") ||
                upperSql.contains("ALTER ") || upperSql.contains("TRUNCATE ")) {
                log.warn("[!] AI attempted to run a forbidden DML/DDL query: {}", sql)
                return@Function SqlResponse("Error: Query contains forbidden keywords. Only SELECT is allowed.")
            }

            try {
                val rows = apexAiJdbcTemplate.queryForList(sql)

                val resultString = if (rows.isEmpty()) {
                    "No data found for the given query."
                } else {
                    rows.toString()
                }

                log.info("[*] Query executed successfully. Returning {} rows to AI.", rows.size)
                SqlResponse(resultString)

            } catch (e: Exception) {
                log.error("[!] SQL Execution Error: {}", e.message)
                SqlResponse("SQL Execution Error: ${e.message}. Please fix the query and try again.")
            }
        }
    }
}