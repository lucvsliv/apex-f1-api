package com.lucvs.apex_f1_api.infrastructure.persistence

import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.infrastructure.persistence.adapter.DriverPersistenceAdapter
import com.lucvs.apex_f1_api.infrastructure.persistence.respository.DriverRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("test")
class VectorSearchIntegrationTest {

    @Autowired
    lateinit var driverPersistenceAdapter: DriverPersistenceAdapter

    @Autowired
    lateinit var driverRepository: DriverRepository // 데이터 초기화를 위해 필요

    @Test
    @DisplayName("Ferrari Driver를 검색하면 Charles Leclerc, Lewis Hamilton이 상위권에 있어야 한다")
    fun searchFerrariDriver() {

        // ==========================================
        // 1. Given: 2026년 테스트 데이터 준비
        // ==========================================
        val drivers = listOf(
            // [정답 그룹] Ferrari (2026년 라인업)
            Driver(number = 16, name = "Charles Leclerc", team = "Ferrari", country = "Monaco", acronym = "LEC"),
            Driver(number = 44, name = "Lewis Hamilton", team = "Ferrari", country = "United Kingdom", acronym = "HAM"),

            // [오답 그룹 1] 강력한 경쟁자 (Red Bull)
            Driver(number = 1, name = "Max Verstappen", team = "Red Bull Racing", country = "Netherlands", acronym = "VER"),

            // [오답 그룹 2] 과거의 페라리 드라이버 (Aston Martin) -> APEX-20 튜닝 효과 검증용
            Driver(number = 14, name = "Fernando Alonso", team = "Aston Martin", country = "Spain", acronym = "ALO"),

            // [오답 그룹 3] 완전히 다른 팀 (McLaren)
            Driver(number = 4, name = "Lando Norris", team = "McLaren", country = "United Kingdom", acronym = "NOR")
        )

        // 2026년 시즌으로 저장
        driverPersistenceAdapter.saveDrivers(drivers, 2026)

        // ==========================================
        // 2. When: 검색 실행
        // ==========================================
        val query = "Who are the drivers from Ferrari in season 2026?"
        val results = driverPersistenceAdapter.searchDrivers(query, 5)

        // ==========================================
        // 3. Then: 검증
        // ==========================================
        assertThat(results).isNotEmpty

        println("[*] 검색 결과 (Top 5) ===")
        results.forEachIndexed { index, it ->
            println("${index + 1}. [${it.driver.team}] ${it.driver.name} (유사도: ${String.format("%.4f", it.similarityScore)})")
        }

        // [검증 1] 상위 2명은 무조건 Ferrari여야 함
        val top2Teams = results.take(2).map { it.driver.team }
        assertThat(top2Teams)
            .describedAs("상위 1, 2등은 Ferrari 팀이어야 합니다.")
            .allMatch { it.contains("Ferrari", ignoreCase = true) }
    }
}