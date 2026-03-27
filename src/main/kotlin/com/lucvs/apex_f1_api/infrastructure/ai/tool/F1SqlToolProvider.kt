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

    // AI가 보내는 요청 구조체
    data class SqlRequest(val query: String)
    // AI에게 돌려줄 응답 구조체
    data class SqlResponse(val result: String)

    @Bean
    @Description("Executes a Read-Only PostgreSQL query to fetch Formula 1 data. Input must be a valid SELECT query.")
    fun executeF1SqlTool(): Function<SqlRequest, SqlResponse> {
        return Function { request ->
            val sql = request.query.trim()
            log.info("[*] Apex-AI generated SQL: {}", sql)

            // 🛡️ 2차 보안 방어벽: 애플리케이션 단에서 위험 키워드 필터링
            val upperSql = sql.uppercase()
            if (upperSql.contains("INSERT ") || upperSql.contains("UPDATE ") ||
                upperSql.contains("DELETE ") || upperSql.contains("DROP ") ||
                upperSql.contains("ALTER ") || upperSql.contains("TRUNCATE ")) {
                log.warn("[!] AI attempted to run a forbidden DML/DDL query: {}", sql)
                return@Function SqlResponse("Error: Query contains forbidden keywords. Only SELECT is allowed.")
            }

            try {
                // 💡 SQL 실행 후 결과를 List<Map<String, Any>> 형태로 받아옵니다.
                // (이전에 JdbcTemplate에 maxRows=100 제한을 걸어두었기 때문에 메모리 폭발 위험이 없습니다.)
                val rows = apexAiJdbcTemplate.queryForList(sql)

                // 결과를 문자열로 변환하여 AI에게 반환
                val resultString = if (rows.isEmpty()) {
                    "No data found for the given query."
                } else {
                    rows.toString()
                }

                log.info("[*] Query executed successfully. Returning {} rows to AI.", rows.size)
                SqlResponse(resultString)

            } catch (e: Exception) {
                log.error("[!] SQL Execution Error: {}", e.message)
                // 문법 오류 등이 나면 에러 메시지를 AI에게 돌려주어 '스스로 쿼리를 고칠 기회'를 줍니다.
                SqlResponse("SQL Execution Error: ${e.message}. Please fix the query and try again.")
            }
        }
    }
}