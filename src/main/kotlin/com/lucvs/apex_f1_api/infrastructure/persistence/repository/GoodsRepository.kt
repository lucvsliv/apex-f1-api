package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.GoodsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface GoodsRepository : JpaRepository<GoodsEntity, Long>