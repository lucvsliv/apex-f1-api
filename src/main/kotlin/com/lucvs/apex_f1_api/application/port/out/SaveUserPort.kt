package com.lucvs.apex_f1_api.application.port.out

import com.lucvs.apex_f1_api.domain.model.User

interface SaveUserPort {
    fun save(user: User): User
}