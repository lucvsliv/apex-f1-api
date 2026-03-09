package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.port.`in`.CheckNicknameUseCase
import com.lucvs.apex_f1_api.application.port.`in`.LoginUseCase
import com.lucvs.apex_f1_api.application.port.`in`.SignUpUseCase
import com.lucvs.apex_f1_api.application.port.`in`.SocialLoginUseCase
import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.infrastructure.api.dto.AuthResponse
import com.lucvs.apex_f1_api.infrastructure.api.dto.LoginRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.SignUpRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.SocialLoginRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val signUpUseCase: SignUpUseCase,
    private val socialLoginUseCase: SocialLoginUseCase,
    private val loginUseCase: LoginUseCase,
    private val checkNicknameUseCase: CheckNicknameUseCase,
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

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<Any> {
        return try {
            val jwtToken = loginUseCase.login(request)
            ResponseEntity.ok(AuthResponse(jwtToken))
        } catch (e: IllegalArgumentException) {
            if (e.message?.contains("소셜") == true) {
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)   // 400
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.message)  // 401
            }
        }
    }

    @GetMapping("/check-nickname")
    fun checkNickname(@RequestParam nickname: String): ResponseEntity<String> {
        return try {
            checkNicknameUseCase.checkNickname(nickname)
            ResponseEntity.ok("사용 가능한 닉네임입니다.")
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        }
    }
}