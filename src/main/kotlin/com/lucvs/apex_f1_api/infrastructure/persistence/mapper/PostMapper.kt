package com.lucvs.apex_f1_api.infrastructure.persistence.mapper

import com.lucvs.apex_f1_api.domain.model.Post
import com.lucvs.apex_f1_api.infrastructure.persistence.entity.PostEntity
import org.springframework.stereotype.Component

@Component
class PostMapper {
    fun toDomain(entity: PostEntity): Post {
        return Post(
            id = entity.id,
            authorId = entity.authorId,
            title = entity.title,
            content = entity.content,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    fun toEntity(domain: Post): PostEntity {
        return PostEntity(
            id = domain.id,
            authorId = domain.authorId,
            title = domain.title,
            content = domain.content,
            createdAt = domain.createdAt,
            updatedAt = domain.updatedAt
        )
    }
}
