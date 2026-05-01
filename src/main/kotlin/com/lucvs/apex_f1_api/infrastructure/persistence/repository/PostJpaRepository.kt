package com.lucvs.apex_f1_api.infrastructure.persistence.repository

import com.lucvs.apex_f1_api.infrastructure.persistence.entity.PostEntity
import org.springframework.data.jpa.repository.JpaRepository

interface PostJpaRepository : JpaRepository<PostEntity, Long>
