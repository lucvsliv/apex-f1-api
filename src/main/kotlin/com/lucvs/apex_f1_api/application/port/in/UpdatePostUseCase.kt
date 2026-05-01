package com.lucvs.apex_f1_api.application.port.`in`

data class UpdatePostCommand(
    val id: Long,
    val requesterId: Long,
    val title: String,
    val content: String
)

interface UpdatePostUseCase {
    fun updatePost(command: UpdatePostCommand)
}
