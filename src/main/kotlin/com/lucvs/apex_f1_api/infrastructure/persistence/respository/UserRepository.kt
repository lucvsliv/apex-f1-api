package com.lucvs.apex_f1_api.infrastructure.persistence.respository

import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Long> {

    fun findByProviderAndProviderId(provider: AuthProvider, providerId: String): UserEntity?
    fun findByEmail(email: String): UserEntity?
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}