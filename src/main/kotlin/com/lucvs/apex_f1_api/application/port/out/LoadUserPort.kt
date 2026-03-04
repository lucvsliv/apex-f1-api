package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.User

interface LoadUserPort {
    fun loadUserById(userId: Long): User?
    fun loadUserByEmail(email: String): User?
}