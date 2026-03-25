package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonDriverEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonDriverId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeasonDriverRepository : JpaRepository<SeasonDriverEntity, SeasonDriverId> {
    // 특정 시즌의 모든 드라이버 기록 조회
    fun findAllByYear(year: Int): List<SeasonDriverEntity>

    // 특정 드라이버의 역대 시즌 기록 조회
    fun findAllByDriverId(driverId: String): List<SeasonDriverEntity>
}