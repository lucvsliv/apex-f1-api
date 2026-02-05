package com.lucvs.apex_f1_api.infrastructure.persistence

import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("local")
class VectorSearchIntegrationTest {

    @Qualifier("driverPersistenceAdapter")
    @Autowired
    lateinit var loadDriverPort: LoadDriverPort

    @Test
    @DisplayName("Ferrari Driver를 검색하면 Charles Leclerc, Lewis Hamilton이 상위권에 있어야 한다")
    fun searchFerrariDriver() {
        // given
        val query = "Who is the driver from team Ferrari?"

        // when
        val results = loadDriverPort.searchDrivers(query, 5)

        // then
        assertThat(results).isNotEmpty

        // result checking
        results.forEach {
            println("${it.driver.name}} : ${it.similarityScore}")
        }

        // validate if there are two Ferrari drivers on the top
        val topDrivers = results.take(2).map { it.driver.team }
        assertThat(topDrivers).allMatch { it.contains("Ferrari", ignoreCase = true) }
    }
}