package com.lucvs.apex_f1_api.domain.model

import java.time.LocalDateTime

data class Post(
    val id: Long? = null,
    val authorId: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
