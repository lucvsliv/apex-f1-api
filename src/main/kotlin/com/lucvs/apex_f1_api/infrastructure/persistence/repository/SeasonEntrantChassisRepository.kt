package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonEntrantChassisEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonEntrantChassisId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeasonEntrantChassisRepository : JpaRepository<SeasonEntrantChassisEntity, SeasonEntrantChassisId> {
    fun findByYear(year: Int): List<SeasonEntrantChassisEntity>
}
