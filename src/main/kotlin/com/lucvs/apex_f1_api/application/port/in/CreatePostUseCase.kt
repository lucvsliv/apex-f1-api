package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.domain.model.Post

interface CreatePostUseCase {
    fun createPost(command: CreatePostCommand): Post
}

data class CreatePostCommand(
    val authorId: Long,
    val title: String,
    val content: String
)
