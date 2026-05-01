package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.Post

interface SavePostPort {
    fun savePost(post: Post): Post
}
