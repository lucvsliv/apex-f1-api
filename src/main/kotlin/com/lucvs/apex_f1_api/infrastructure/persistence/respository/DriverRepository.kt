package com.lucvs.apex_f1_api.infrastructure.persistence.respository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.DriverEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DriverRepository : JpaRepository<DriverEntity, Long> {

    fun findByNumberAndSeason(number: Int, season: Int): DriverEntity?
}