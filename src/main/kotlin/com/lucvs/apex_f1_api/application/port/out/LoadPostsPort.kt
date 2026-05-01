package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.Post

interface LoadPostsPort {
    fun loadAllPosts(): List<Post>
    fun loadPost(id: Long): Post?
}
