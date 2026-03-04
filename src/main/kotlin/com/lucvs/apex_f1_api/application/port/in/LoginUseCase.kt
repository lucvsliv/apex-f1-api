package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.infrastructure.api.dto.LoginRequest

interface LoginUseCase {
    fun login(request: LoginRequest): String
}