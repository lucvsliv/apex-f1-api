package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.GetSeasonDriverUseCase
import com.lucvs.apex_f1_api.application.port.out.LoadCountryPort
import com.lucvs.apex_f1_api.application.port.out.LoadDriverPort
import com.lucvs.apex_f1_api.application.port.out.LoadEntrantPort
import com.lucvs.apex_f1_api.application.port.out.LoadSeasonDriverPort
import com.lucvs.apex_f1_api.application.port.out.LoadSeasonEntrantDriverPort
import com.lucvs.apex_f1_api.infrastructure.api.dto.SeasonDriverDetailResponse
import com.lucvs.apex_f1_api.infrastructure.api.dto.SeasonDriverSummaryResponse
import org.springframework.stereotype.Service

@Service
class SeasonDriverService(
    private val loadSeasonDriverPort: LoadSeasonDriverPort,
    private val loadDriverPort: LoadDriverPort,
    private val loadSeasonEntrantDriverPort: LoadSeasonEntrantDriverPort,
    private val loadEntrantPort: LoadEntrantPort,
    private val loadCountryPort: LoadCountryPort
) : GetSeasonDriverUseCase {

    override fun getSeasonDrivers(year: Int): List<SeasonDriverSummaryResponse> {
        // 1. 해당 시즌의 모든 시즌-드라이버 & 시즌-컨스트럭터 정보 조회
        val seasonDrivers = loadSeasonDriverPort.loadSeasonDrivers(year)
        val seasonEntrants = loadSeasonEntrantDriverPort.loadByYear(year).associateBy { it.driverId }

        // 2. 관련 Driver, Entrant, Country 데이터 조회
        val driverIds = seasonDrivers.map { it.driverId }
        val drivers = loadDriverPort.loadAllByIds(driverIds).associateBy { it.id }

        val entrantIds = seasonEntrants.values.map { it.entrantId }.distinct()
        val entrants = loadEntrantPort.loadAllByIds(entrantIds).associateBy { it.id }

        val countryIds = drivers.values.map { it.nationalityCountryId }.distinct()
        val countries = loadCountryPort.loadAllByIds(countryIds).associateBy { it.id }

        return seasonDrivers
            .sortedBy { it.positionNumber ?: Int.MAX_VALUE } // 챔피언십 순위대로 정렬
            .mapNotNull { seasonStat ->
                val driver = drivers[seasonStat.driverId] ?: return@mapNotNull null
                val country = countries[driver.nationalityCountryId] ?: return@mapNotNull null

                val entrantId = seasonEntrants[seasonStat.driverId]?.entrantId
                val entrant = entrantId?.let { entrants[it] }

                SeasonDriverSummaryResponse.of(driver, country, entrant)
            }
    }

    override fun getSeasonDriverDetail(year: Int, driverId: String): SeasonDriverDetailResponse {
        val seasonStat = loadSeasonDriverPort.loadByDriverId(driverId).find { it.year == year }
            ?: throw IllegalArgumentException("SeasonDriver not found for year $year and driverId $driverId")

        val driver = loadDriverPort.loadAllByIds(listOf(driverId)).firstOrNull()
            ?: throw IllegalArgumentException("Driver not found for driverId $driverId")

        val country = loadCountryPort.loadAllByIds(listOf(driver.nationalityCountryId)).firstOrNull()
            ?: throw IllegalArgumentException("Country not found for countryId ${driver.nationalityCountryId}")

        val entrantId = loadSeasonEntrantDriverPort.loadByYear(year).find { it.driverId == driverId }?.entrantId
        val entrant = entrantId?.let { loadEntrantPort.loadAllByIds(listOf(it)).firstOrNull() }

        return SeasonDriverDetailResponse.of(driver, country, entrant, seasonStat)
    }
}