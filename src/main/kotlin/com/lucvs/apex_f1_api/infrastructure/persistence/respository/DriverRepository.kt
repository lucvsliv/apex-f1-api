package com.lucvs.apex_f1_api.infrastructure.persistence.respository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.DriverEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface DriverRepository : JpaRepository<DriverEntity, Int> {

    /**
     * Driver 유사도 검색 - Cosine Distance
     *
     * @param vector
     * @param limit
     * @return
     */
    @Query(
        value = """
            SELECT d.*, (d.embedding <=> cast(:vector as vector)) as distance
            FROM drivers d
            ORDER BY distance ASC
            LIMIT :limit
        """,
        nativeQuery = true
    )
    fun searchSimilarDrivers(
        @Param("vector") vector: List<Double>,
        @Param("limit") limit: Int
    ) : List<Array<Any>>
}