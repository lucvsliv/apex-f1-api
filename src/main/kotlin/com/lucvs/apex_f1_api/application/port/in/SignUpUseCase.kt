package com.lucvs.apex_f1_api.application.port.`in`

import com.lucvs.apex_f1_api.infrastructure.api.dto.SignUpRequest

interface SignUpUseCase {
    fun signUp(request: SignUpRequest)
}