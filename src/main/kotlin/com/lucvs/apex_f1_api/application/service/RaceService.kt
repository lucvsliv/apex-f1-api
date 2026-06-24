package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.dto.RaceScheduleResponse
import com.lucvs.apex_f1_api.application.dto.SessionDto
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.RaceEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.RaceRepository
import com.lucvs.apex_f1_api.application.dto.RaceResultResponse
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.RaceDataRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

@Service
class RaceService(
    private val raceRepository: RaceRepository,
    private val raceDataRepository: RaceDataRepository
) {
    @Transactional(readOnly = true)
    fun getSchedulesByYear(year: Int): List<RaceScheduleResponse> {
        val races = raceRepository.findByYearOrderByRoundAsc(year)
        
        val now = LocalDate.now(ZoneId.of("Asia/Seoul"))
        // 다음 다가오는 레이스 1개만 isCurrent = true 로 설정
        val currentRaceId = races.firstOrNull { it.date.isAfter(now) || it.date.isEqual(now) }?.id

        return races.map { race ->
            RaceScheduleResponse(
                id = race.id,
                isCurrent = race.id == currentRaceId,
                round = "Round ${race.round}",
                countryCode = race.circuit.country.alpha3Code,
                countryCodeISO = race.circuit.country.alpha2Code,
                grandPrixId = race.grandPrixId,
                name = race.officialName,
                date = formatRaceDateRange(race),
                circuit = race.circuit.name,
                country = race.circuit.country.name,
                city = race.circuit.placeName,
                sessions = buildSessions(race)
            )
        }
    }

    private fun formatRaceDateRange(race: RaceEntity): String {
        val start = race.freePractice1Date ?: race.date.minusDays(2)
        val end = race.date

        val startMonth = start.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH))
        val endMonth = end.format(DateTimeFormatter.ofPattern("MMM", Locale.ENGLISH))
        val startDay = start.dayOfMonth
        val endDay = end.dayOfMonth

        return if (startMonth == endMonth) {
            "$startMonth $startDay-$endDay"
        } else {
            "$startMonth $startDay-$endMonth $endDay"
        }
    }

    private fun buildSessions(race: RaceEntity): List<SessionDto> {
        val sessions = mutableListOf<SessionDto>()

        fun addSession(name: String, date: LocalDate?, time: String?) {
            if (date != null) {
                val dateStr = date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd(EEE)", Locale.ENGLISH))
                val timeStr = time ?: "TBD"
                sessions.add(SessionDto(name, "$dateStr $timeStr"))
            }
        }

        // 시간 순서에 맞게 세션 추가
        addSession("Practice 1", race.freePractice1Date, race.freePractice1Time)
        
        if (race.sprintQualifyingDate != null || race.sprintRaceDate != null) {
            addSession("Sprint Qualifying", race.sprintQualifyingDate, race.sprintQualifyingTime)
            addSession("Sprint", race.sprintRaceDate, race.sprintRaceTime)
        } else {
            addSession("Practice 2", race.freePractice2Date, race.freePractice2Time)
            addSession("Practice 3", race.freePractice3Date, race.freePractice3Time)
        }

        addSession("Qualifying", race.qualifyingDate, race.qualifyingTime)
        addSession("Race", race.date, race.time)

        return sessions
    }

    @Transactional(readOnly = true)
    fun getRaceResults(raceId: Int): List<RaceResultResponse> {
        val results = raceDataRepository.findByRaceIdAndTypeOrderByPositionDisplayOrder(raceId, "RACE_RESULT")
        return results.map { result ->
            RaceResultResponse(
                position = result.positionText,
                driverId = result.driver.id,
                driverName = result.driver.fullName,
                driverNumber = result.driverNumber,
                constructorId = result.constructorId,
                laps = result.raceLaps,
                timeOrGap = result.raceTime ?: result.raceGap,
                points = result.racePoints,
                status = result.raceReasonRetired ?: if (result.raceTime != null || result.raceGap != null) "Finished" else ""
            )
        }
    }
}
