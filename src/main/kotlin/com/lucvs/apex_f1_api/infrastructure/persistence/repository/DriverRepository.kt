package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.DriverEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DriverRepository : JpaRepository<DriverEntity, Long> {

    fun findByNumberAndSeason(number: Int, season: Int): DriverEntity?
}