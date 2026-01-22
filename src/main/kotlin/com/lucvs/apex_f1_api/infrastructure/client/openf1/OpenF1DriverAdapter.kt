package com.lucvs.apex_f1_api.infrastructure.client.openf1

import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.infrastructure.client.openf1.dto.OpenF1DriverResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

/**
 * LoadDriverPort 구현체(Adapter)
 * - 실제 OpenF1 API 엔드포인트를 호출
 * - 응답 DTO -> Driver Model로 변환
 */
@Component
class OpenF1DriverAdapter(
    private val restClient: RestClient,
    @Value("\${openf1.api.url:https://api.openf1.org/v1}") private val apiUrl: String
) : LoadDriverPort {

    private val logger = LoggerFactory.getLogger(javaClass)

    override fun loadDrivers(): List<Driver> {
        logger.debug("Fetching drivers from OpenF1 API...")

        return try {
            val response = restClient.get()
                .uri("$apiUrl/drivers?session_key=latest")
                .retrieve()
                .body(Array<OpenF1DriverResponse>::class.java) ?: emptyArray()

            response.map { dto ->
                Driver(
                    number = dto.driverNumber,
                    name = dto.fullName,
                    nameAcronym = dto.nameAcronym,
                    team = dto.teamName ?: "Unknown Team",
                    country = dto.countryCode ?: "Unknown",
                    profileImageUrl = dto.headshotUrl
                )
            }.distinctBy { it.number }

        } catch (e: Exception) {
            logger.error("Failed to fetch drivers from OpenF1 API", e)
            emptyList()
        }
    }
}