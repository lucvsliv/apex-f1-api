package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonDriverId
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "season_driver")
@IdClass(SeasonDriverId::class)
class SeasonDriverEntity(

    @Id
    @Column(name = "year", nullable = false)
    val year: Int,

    @Id
    @Column(name = "driver_id", length = 100, nullable = false)
    val driverId: String,

    @Column(name = "position_number")
    val positionNumber: Int? = null,

    @Column(name = "position_text", length = 4)
    val positionText: String? = null,

    @Column(name = "best_starting_grid_position")
    val bestStartingGridPosition: Int? = null,

    @Column(name = "best_race_result")
    val bestRaceResult: Int? = null,

    @Column(name = "best_sprint_race_result")
    val bestSprintRaceResult: Int? = null,

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

    // 필요하다면 @ManyToOne 연관관계 매핑을 추가할 수 있습니다.
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "driver_id", insertable = false, updatable = false)
    // val driver: DriverEntity? = null
)