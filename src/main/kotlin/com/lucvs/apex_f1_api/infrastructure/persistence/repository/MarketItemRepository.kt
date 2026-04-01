package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.store.MarketItemEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MarketItemRepository : JpaRepository<MarketItemEntity, Long>