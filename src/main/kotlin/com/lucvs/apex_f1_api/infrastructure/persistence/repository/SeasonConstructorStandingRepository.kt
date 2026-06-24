package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonConstructorStandingEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonConstructorStandingId
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.projection.ConstructorStandingProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SeasonConstructorStandingRepository : JpaRepository<SeasonConstructorStandingEntity, SeasonConstructorStandingId> {

    @Query(
        value = """
            SELECT
                scs.position_number as position,
                scs.constructor_id as teamId,
                COALESCE(e.name, scs.constructor_id) as name,
                sci.team_color as teamColor,
                scs.points as points
            FROM season_constructor_standing scs
            LEFT JOIN entrant e ON e.id = scs.constructor_id
            LEFT JOIN season_constructor_info sci ON sci.constructor_id = scs.constructor_id AND sci.year = scs.year
            WHERE scs.year = :year
            ORDER BY scs.position_number ASC
        """,
        nativeQuery = true
    )
    fun findConstructorStandingsByYear(@Param("year") year: Int): List<ConstructorStandingProjection>
}
