package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.SeasonEntrantDriverEntity
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.key.SeasonEntrantDriverId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SeasonEntrantDriverRepository : JpaRepository<SeasonEntrantDriverEntity, SeasonEntrantDriverId> {

    // 특정 시즌의 모든 팀-선수 매핑 정보 조회
    fun findAllByYear(year: Int): List<SeasonEntrantDriverEntity>

    // 특정 시즌, 특정 선수의 소속 팀 정보 조회 (시즌 중 이적할 수 있으므로 List 반환)
    fun findAllByYearAndDriverId(year: Int, driverId: String): List<SeasonEntrantDriverEntity>
}