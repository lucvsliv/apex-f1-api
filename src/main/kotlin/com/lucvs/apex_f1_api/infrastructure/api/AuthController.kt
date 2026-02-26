package com.lucvs.apex_f1_api.infrastructure.api

import com.lucvs.apex_f1_api.application.service.AuthService
import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.infrastructure.api.dto.AuthResponse
import com.lucvs.apex_f1_api.infrastructure.api.dto.SocialLoginRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/social/login")
    fun socialLogin(@RequestBody request: SocialLoginRequest): ResponseEntity<AuthResponse> {
        val provider = try {
            AuthProvider.valueOf(request.provider.uppercase())
        } catch (e: Exception) {
            throw IllegalArgumentException("잘못된 소셜 제공자입니다: ${request.provider}")
        }

        val jwtToken = authService.socialLogin(provider, request.accessToken)

        return ResponseEntity.ok(AuthResponse(jwtToken))
    }
}