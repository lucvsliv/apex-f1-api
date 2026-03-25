package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate

@Entity
@Table(name = "driver")
class DriverEntity(

    @Id
    @Column(name = "id", length = 100, nullable = false)
    val id: String,

    @Column(name = "name", length = 100, nullable = false)
    val name: String,

    @Column(name = "first_name", length = 100, nullable = false)
    val firstName: String,

    @Column(name = "last_name", length = 100, nullable = false)
    val lastName: String,

    @Column(name = "full_name", length = 100, nullable = false)
    val fullName: String,

    @Column(name = "abbreviation", length = 3, nullable = false)
    val abbreviation: String,

    @Column(name = "permanent_number", length = 2)
    val permanentNumber: String? = null,

    @Column(name = "gender", length = 6, nullable = false)
    val gender: String,

    @Column(name = "date_of_birth", nullable = false)
    val dateOfBirth: LocalDate,

    @Column(name = "date_of_death")
    val dateOfDeath: LocalDate? = null,

    @Column(name = "place_of_birth", length = 100, nullable = false)
    val placeOfBirth: String,

    @Column(name = "country_of_birth_country_id", length = 100, nullable = false)
    val countryOfBirthCountryId: String,

    @Column(name = "nationality_country_id", length = 100, nullable = false)
    val nationalityCountryId: String,

    @Column(name = "second_nationality_country_id", length = 100)
    val secondNationalityCountryId: String? = null,

    @Column(name = "best_championship_position")
    val bestChampionshipPosition: Int? = null,

    @Column(name = "best_starting_grid_position")
    val bestStartingGridPosition: Int? = null,

    @Column(name = "best_race_result")
    val bestRaceResult: Int? = null,

    @Column(name = "best_sprint_race_result")
    val bestSprintRaceResult: Int? = null,

    @Column(name = "total_championship_wins", nullable = false)
    val totalChampionshipWins: Int = 0,

    @Column(name = "total_race_entries", nullable = false)
    val totalRaceEntries: Int = 0,

    @Column(name = "total_race_starts", nullable = false)
    val totalRaceStarts: Int = 0,

    @Column(name = "total_race_wins", nullable = false)
    val totalRaceWins: Int = 0,

    @Column(name = "total_race_laps", nullable = false)
    val totalRaceLaps: Int = 0,

    @Column(name = "total_podiums", nullable = false)
    val totalPodiums: Int = 0,

    @Column(name = "total_points", precision = 8, scale = 2, nullable = false)
    val totalPoints: BigDecimal = BigDecimal.ZERO,

    @Column(name = "total_championship_points", precision = 8, scale = 2, nullable = false)
    val totalChampionshipPoints: BigDecimal = BigDecimal.ZERO,

    @Column(name = "total_pole_positions", nullable = false)
    val totalPolePositions: Int = 0,

    @Column(name = "total_fastest_laps", nullable = false)
    val totalFastestLaps: Int = 0,

    @Column(name = "total_sprint_race_starts", nullable = false)
    val totalSprintRaceStarts: Int = 0,

    @Column(name = "total_sprint_race_wins", nullable = false)
    val totalSprintRaceWins: Int = 0,

    @Column(name = "total_driver_of_the_day", nullable = false)
    val totalDriverOfTheDay: Int = 0,

    @Column(name = "total_grand_slams", nullable = false)
    val totalGrandSlams: Int = 0
)