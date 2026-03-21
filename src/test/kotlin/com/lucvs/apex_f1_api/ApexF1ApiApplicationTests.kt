package com.lucvs.apex_f1_api

import io.github.bucket4j.distributed.proxy.ProxyManager
import io.lettuce.core.RedisClient
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean

@SpringBootTest
@ActiveProfiles("test") // 💡 1. application-test.yml을 읽도록 강제합니다.
class ApexF1ApiApplicationTests {

	// 💡 2. 스프링이 켜질 때 진짜 Redis를 찾지 못하도록 Mock 객체를 쥐여줍니다.
	@MockitoBean
	private lateinit var redisClient: RedisClient

	@MockitoBean
	private lateinit var bucket4jProxyManager: ProxyManager<ByteArray>

	@Test
	fun contextLoads() {
		// 이 메서드가 에러 없이 통과하면 스프링 컨텍스트가 정상적으로 로드된 것입니다!
	}
}