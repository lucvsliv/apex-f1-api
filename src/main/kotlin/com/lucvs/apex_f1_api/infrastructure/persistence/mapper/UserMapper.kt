package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.User
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toDomain(entity: UserEntity): User {
        return User(
            id              = entity.id,
            provider        = entity.provider,
            providerId      = entity.providerId,
            email           = entity.email,
            password        = entity.password,
            nickname        = entity.nickname,
            profileImageUrl = entity.profileImageUrl,
            role            = entity.role
        )
    }

    fun toEntity(domain: User): UserEntity {
        return UserEntity(
            id              = domain.id,
            provider        = domain.provider,
            providerId      = domain.providerId,
            email           = domain.email,
            password        = domain.password,
            nickname        = domain.nickname,
            profileImageUrl = domain.profileImageUrl,
            role            = domain.role
        )
    }
}