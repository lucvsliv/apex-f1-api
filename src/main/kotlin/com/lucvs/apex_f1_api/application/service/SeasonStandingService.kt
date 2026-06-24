package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.dto.DriverRankResponse
import com.lucvs.apex_f1_api.application.dto.TeamRankResponse
import com.lucvs.apex_f1_api.application.port.`in`.GetSeasonStandingUseCase
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SeasonConstructorStandingRepository
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.SeasonDriverStandingRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SeasonStandingService(
    private val driverStandingRepository: SeasonDriverStandingRepository,
    private val constructorStandingRepository: SeasonConstructorStandingRepository
) : GetSeasonStandingUseCase {

    @Transactional(readOnly = true)
    override fun getDriverStandings(year: Int): List<DriverRankResponse> {
        val standings = driverStandingRepository.findDriverStandingsByYear(year)
        return standings.map {
            DriverRankResponse(
                position = it.getPosition(),
                driverId = it.getDriverId(),
                name = it.getName() ?: it.getDriverId(),
                team = it.getTeam() ?: "Unknown",
                teamColor = it.getTeamColor(),
                points = it.getPoints()
            )
        }
    }

    @Transactional(readOnly = true)
    override fun getConstructorStandings(year: Int): List<TeamRankResponse> {
        val standings = constructorStandingRepository.findConstructorStandingsByYear(year)
        return standings.map {
            TeamRankResponse(
                position = it.getPosition(),
                teamId = it.getTeamId(),
                name = it.getName() ?: it.getTeamId(),
                teamColor = it.getTeamColor(),
                points = it.getPoints()
            )
        }
    }
}
