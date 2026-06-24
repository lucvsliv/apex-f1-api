package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonDriverStandingEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonDriverStandingId
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.projection.DriverStandingProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SeasonDriverStandingRepository : JpaRepository<SeasonDriverStandingEntity, SeasonDriverStandingId> {

    @Query(
        value = """
            SELECT
                sds.position_number as position,
                sds.driver_id as driverId,
                d.full_name as name,
                (SELECT sed.constructor_id FROM season_entrant_driver sed WHERE sed.driver_id = sds.driver_id AND sed.year = sds.year LIMIT 1) as team,
                (SELECT sci.team_color FROM season_constructor_info sci WHERE sci.constructor_id = (SELECT sed.constructor_id FROM season_entrant_driver sed WHERE sed.driver_id = sds.driver_id AND sed.year = sds.year LIMIT 1) AND sci.year = sds.year LIMIT 1) as teamColor,
                sds.points as points
            FROM season_driver_standing sds
            LEFT JOIN driver d ON d.id = sds.driver_id
            WHERE sds.year = :year
            ORDER BY sds.position_number ASC
        """,
        nativeQuery = true
    )
    fun findDriverStandingsByYear(@Param("year") year: Int): List<DriverStandingProjection>
}
