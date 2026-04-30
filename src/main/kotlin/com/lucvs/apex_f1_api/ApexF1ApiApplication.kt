package com.lucvs.apex_f1_api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component

@EnableKafka
@EnableScheduling
@SpringBootApplication
@ConfigurationPropertiesScan
class ApexF1ApiApplication

fun main(args: Array<String>) {
	runApplication<ApexF1ApiApplication>(*args)
}

@Component
class DummyConsumer {
	@KafkaListener(topics = ["coupon-issue-topic"], groupId = "dummy-test-group")
	fun listen(message: String) {
		println("🚨🚨🚨 [더미 컨슈머 작동 성공!] 받은 메시지: $message 🚨🚨🚨")
	}
}
