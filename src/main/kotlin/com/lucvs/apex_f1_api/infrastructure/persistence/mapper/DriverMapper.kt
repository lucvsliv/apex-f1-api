package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Driver
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.DriverEntity
import org.springframework.stereotype.Component

@Component
class DriverMapper {

    // Entity (DB) -> Domain (Core) 변환
    fun toDomain(entity: DriverEntity): Driver {
        return Driver(
            id = entity.id,
            name = entity.name,
            firstName = entity.firstName,
            lastName = entity.lastName,
            fullName = entity.fullName,
            abbreviation = entity.abbreviation,
            permanentNumber = entity.permanentNumber,
            gender = entity.gender,
            dateOfBirth = entity.dateOfBirth,
            dateOfDeath = entity.dateOfDeath,
            placeOfBirth = entity.placeOfBirth,
            countryOfBirthCountryId = entity.countryOfBirthCountryId,
            nationalityCountryId = entity.nationalityCountryId,
            secondNationalityCountryId = entity.secondNationalityCountryId,
            bestChampionshipPosition = entity.bestChampionshipPosition,
            bestStartingGridPosition = entity.bestStartingGridPosition,
            bestRaceResult = entity.bestRaceResult,
            bestSprintRaceResult = entity.bestSprintRaceResult,
            totalChampionshipWins = entity.totalChampionshipWins,
            totalRaceEntries = entity.totalRaceEntries,
            totalRaceStarts = entity.totalRaceStarts,
            totalRaceWins = entity.totalRaceWins,
            totalRaceLaps = entity.totalRaceLaps,
            totalPodiums = entity.totalPodiums,
            totalPoints = entity.totalPoints,
            totalChampionshipPoints = entity.totalChampionshipPoints,
            totalPolePositions = entity.totalPolePositions,
            totalFastestLaps = entity.totalFastestLaps,
            totalSprintRaceStarts = entity.totalSprintRaceStarts,
            totalSprintRaceWins = entity.totalSprintRaceWins,
            totalDriverOfTheDay = entity.totalDriverOfTheDay,
            totalGrandSlams = entity.totalGrandSlams
        )
    }

    // Domain (Core) -> Entity (DB) 변환
    fun toEntity(domain: Driver): DriverEntity {
        return DriverEntity(
            id = domain.id,
            name = domain.name,
            firstName = domain.firstName,
            lastName = domain.lastName,
            fullName = domain.fullName,
            abbreviation = domain.abbreviation,
            permanentNumber = domain.permanentNumber,
            gender = domain.gender,
            dateOfBirth = domain.dateOfBirth,
            dateOfDeath = domain.dateOfDeath,
            placeOfBirth = domain.placeOfBirth,
            countryOfBirthCountryId = domain.countryOfBirthCountryId,
            nationalityCountryId = domain.nationalityCountryId,
            secondNationalityCountryId = domain.secondNationalityCountryId,
            bestChampionshipPosition = domain.bestChampionshipPosition,
            bestStartingGridPosition = domain.bestStartingGridPosition,
            bestRaceResult = domain.bestRaceResult,
            bestSprintRaceResult = domain.bestSprintRaceResult,
            totalChampionshipWins = domain.totalChampionshipWins,
            totalRaceEntries = domain.totalRaceEntries,
            totalRaceStarts = domain.totalRaceStarts,
            totalRaceWins = domain.totalRaceWins,
            totalRaceLaps = domain.totalRaceLaps,
            totalPodiums = domain.totalPodiums,
            totalPoints = domain.totalPoints,
            totalChampionshipPoints = domain.totalChampionshipPoints,
            totalPolePositions = domain.totalPolePositions,
            totalFastestLaps = domain.totalFastestLaps,
            totalSprintRaceStarts = domain.totalSprintRaceStarts,
            totalSprintRaceWins = domain.totalSprintRaceWins,
            totalDriverOfTheDay = domain.totalDriverOfTheDay,
            totalGrandSlams = domain.totalGrandSlams
        )
    }
}