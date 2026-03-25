package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.SeasonDriver
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonDriverEntity
import org.springframework.stereotype.Component

@Component
class SeasonDriverMapper {

    // Entity (DB) -> Domain (Core) 변환
    fun toDomain(entity: SeasonDriverEntity): SeasonDriver {
        return SeasonDriver(
            year                        = entity.year,
            driverId                    = entity.driverId,
            positionNumber              = entity.positionNumber,
            positionText                = entity.positionText,
            bestStartingGridPosition    = entity.bestStartingGridPosition,
            bestRaceResult              = entity.bestRaceResult,
            bestSprintRaceResult        = entity.bestSprintRaceResult,
            totalRaceEntries            = entity.totalRaceEntries,
            totalRaceStarts             = entity.totalRaceStarts,
            totalRaceWins               = entity.totalRaceWins,
            totalRaceLaps               = entity.totalRaceLaps,
            totalPodiums                = entity.totalPodiums,
            totalPoints                 = entity.totalPoints,
            totalPolePositions          = entity.totalPolePositions,
            totalFastestLaps            = entity.totalFastestLaps,
            totalSprintRaceStarts       = entity.totalSprintRaceStarts,
            totalSprintRaceWins         = entity.totalSprintRaceWins,
            totalDriverOfTheDay         = entity.totalDriverOfTheDay,
            totalGrandSlams             = entity.totalGrandSlams
        )
    }

    // Domain (Core) -> Entity (DB) 변환
    fun toEntity(domain: SeasonDriver): SeasonDriverEntity {
        return SeasonDriverEntity(
            year                        = domain.year,
            driverId                    = domain.driverId,
            positionNumber              = domain.positionNumber,
            positionText                = domain.positionText,
            bestStartingGridPosition    = domain.bestStartingGridPosition,
            bestRaceResult              = domain.bestRaceResult,
            bestSprintRaceResult        = domain.bestSprintRaceResult,
            totalRaceEntries            = domain.totalRaceEntries,
            totalRaceStarts             = domain.totalRaceStarts,
            totalRaceWins               = domain.totalRaceWins,
            totalRaceLaps               = domain.totalRaceLaps,
            totalPodiums                = domain.totalPodiums,
            totalPoints                 = domain.totalPoints,
            totalPolePositions          = domain.totalPolePositions,
            totalFastestLaps            = domain.totalFastestLaps,
            totalSprintRaceStarts       = domain.totalSprintRaceStarts,
            totalSprintRaceWins         = domain.totalSprintRaceWins,
            totalDriverOfTheDay         = domain.totalDriverOfTheDay,
            totalGrandSlams             = domain.totalGrandSlams
        )
    }
}