package com.lucvs.apex_f1_api.infrastructure.api.dto

import com.lucvs.apex_f1_api.domain.model.Post
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val authorId: Long,
    val authorNickname: String,
    val authorProfileImageUrl: String?,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) {
    companion object {
        fun from(post: Post, authorNickname: String, authorProfileImageUrl: String?): PostResponse {
            return PostResponse(
                id = post.id ?: throw IllegalStateException("Post ID cannot be null"),
                authorId = post.authorId,
                authorNickname = authorNickname,
                authorProfileImageUrl = authorProfileImageUrl,
                title = post.title,
                content = post.content,
                createdAt = post.createdAt,
                updatedAt = post.updatedAt
            )
        }
    }
}
