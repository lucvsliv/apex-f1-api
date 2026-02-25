package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.User
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            provider = entity.provider,
            providerId = entity.providerId,
            email = entity.email,
            nickname = entity.nickname,
            role = entity.role
        )
    }

    fun toEntity(user: User): UserEntity {
        return UserEntity(
            id = user.id,
            provider = user.provider,
            providerId = user.providerId,
            email = user.email,
            nickname = user.nickname,
            role = user.role
        )
    }
}