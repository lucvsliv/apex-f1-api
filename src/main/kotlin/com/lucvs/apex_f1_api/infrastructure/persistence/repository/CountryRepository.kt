package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.CountryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<CountryEntity, String>