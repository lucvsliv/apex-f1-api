package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.domain.model.User

interface UpdateUserUseCase {
    fun updateUser(userId: Long, nickname: String?, profileImageUrl: String?): User
}
