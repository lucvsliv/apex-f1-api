package com.lucvs.apex_f1_api.infrastructure.api

import com.lucvs.apex_f1_api.application.port.`in`.SignUpUseCase
import com.lucvs.apex_f1_api.application.port.`in`.SocialLoginUseCase
import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.infrastructure.api.dto.AuthResponse
import com.lucvs.apex_f1_api.infrastructure.api.dto.SignUpRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.SocialLoginRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val socialLoginUseCase: SocialLoginUseCase,
    private val signUpUseCase: SignUpUseCase
) {

    @PostMapping("/signup")
    fun signUp(@RequestBody request: SignUpRequest): ResponseEntity<String> {
        return try {
            signUpUseCase.signUp(request)
            ResponseEntity.status(HttpStatus.CREATED).body("회원가입 성공")
        } catch (e: IllegalArgumentException) {
            // 중복 -> 409 Conflict 반환
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        }
    }

    @PostMapping("/social/login")
    fun socialLogin(@RequestBody request: SocialLoginRequest): ResponseEntity<AuthResponse> {
        val provider = try {
            AuthProvider.valueOf(request.provider.uppercase())
        } catch (e: Exception) {
            throw IllegalArgumentException("잘못된 소셜 제공자입니다: ${request.provider}")
        }

        val jwtToken = socialLoginUseCase.socialLogin(provider, request.accessToken)

        return ResponseEntity.ok(AuthResponse(jwtToken))
    }
}