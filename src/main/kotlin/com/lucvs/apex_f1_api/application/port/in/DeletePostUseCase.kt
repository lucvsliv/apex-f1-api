package com.lucvs.apex_f1_api.application.port.`in`

interface DeletePostUseCase {
    fun deletePost(id: Long, requesterId: Long)
}
