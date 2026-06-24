package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonEntrantConstructorEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonEntrantConstructorId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeasonEntrantConstructorRepository : JpaRepository<SeasonEntrantConstructorEntity, SeasonEntrantConstructorId> {
    fun findByYear(year: Int): List<SeasonEntrantConstructorEntity>
}
