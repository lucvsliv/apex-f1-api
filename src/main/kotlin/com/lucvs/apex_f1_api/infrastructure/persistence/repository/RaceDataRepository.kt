package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.RaceDataEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.RaceDataId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RaceDataRepository : JpaRepository<RaceDataEntity, RaceDataId> {
    fun findByRaceIdAndTypeOrderByPositionDisplayOrder(raceId: Int, type: String): List<RaceDataEntity>
}
