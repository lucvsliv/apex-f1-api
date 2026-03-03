package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.User

interface ManageUserPort {
    fun saveUser(user: User): User
    fun existsByEmail(email: String): Boolean
    fun existsByNickname(nickname: String): Boolean
}