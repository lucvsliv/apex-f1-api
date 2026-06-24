package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "race")
class RaceEntity(
    @Id
    @Column(name = "id", nullable = false)
    val id: Int,

    @Column(name = "year", nullable = false)
    val year: Int,

    @Column(name = "round", nullable = false)
    val round: Int,

    @Column(name = "date", nullable = false)
    val date: LocalDate,

    @Column(name = "time", length = 5)
    val time: String? = null,

    @Column(name = "grand_prix_id", length = 100, nullable = false)
    val grandPrixId: String,

    @Column(name = "official_name", length = 100, nullable = false)
    val officialName: String,

    @Column(name = "qualifying_format", length = 20, nullable = false)
    val qualifyingFormat: String,

    @Column(name = "sprint_qualifying_format", length = 20)
    val sprintQualifyingFormat: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "circuit_id")
    val circuit: CircuitEntity,

    @Column(name = "free_practice_1_date")
    val freePractice1Date: LocalDate? = null,

    @Column(name = "free_practice_1_time", length = 5)
    val freePractice1Time: String? = null,

    @Column(name = "free_practice_2_date")
    val freePractice2Date: LocalDate? = null,

    @Column(name = "free_practice_2_time", length = 5)
    val freePractice2Time: String? = null,

    @Column(name = "free_practice_3_date")
    val freePractice3Date: LocalDate? = null,

    @Column(name = "free_practice_3_time", length = 5)
    val freePractice3Time: String? = null,

    @Column(name = "qualifying_date")
    val qualifyingDate: LocalDate? = null,

    @Column(name = "qualifying_time", length = 5)
    val qualifyingTime: String? = null,

    @Column(name = "sprint_qualifying_date")
    val sprintQualifyingDate: LocalDate? = null,

    @Column(name = "sprint_qualifying_time", length = 5)
    val sprintQualifyingTime: String? = null,

    @Column(name = "sprint_race_date")
    val sprintRaceDate: LocalDate? = null,

    @Column(name = "sprint_race_time", length = 5)
    val sprintRaceTime: String? = null
)
