package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.CouponPolicyEntity
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CouponPolicyRepository : JpaRepository<CouponPolicyEntity, Long> {

    // 동시성 제어 Lock
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CouponPolicyEntity c WHERE c.id = :id")
    fun findByIdForUpdate(@Param("id") id: Long): CouponPolicyEntity?
}