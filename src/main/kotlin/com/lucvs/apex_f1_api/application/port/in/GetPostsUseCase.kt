package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.domain.model.Post

interface GetPostsUseCase {
    fun getAllPosts(): List<Post>
    fun getPost(id: Long): Post?
}
