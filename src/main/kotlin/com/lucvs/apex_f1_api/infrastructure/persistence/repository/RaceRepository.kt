package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.RaceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RaceRepository : JpaRepository<RaceEntity, Int> {
    fun findByYearOrderByRoundAsc(year: Int): List<RaceEntity>
}
