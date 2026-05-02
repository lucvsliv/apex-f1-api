package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.port.`in`.CheckNicknameUseCase
import com.lucvs.apex_f1_api.application.port.`in`.LoginUseCase
import com.lucvs.apex_f1_api.application.port.`in`.SignUpUseCase
import com.lucvs.apex_f1_api.application.port.`in`.SocialLoginUseCase
import com.lucvs.apex_f1_api.application.port.`in`.SendOtpUseCase
import com.lucvs.apex_f1_api.application.port.`in`.VerifyOtpUseCase
import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.infrastructure.api.dto.AuthResponse
import com.lucvs.apex_f1_api.infrastructure.api.dto.LoginRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.SignUpRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.SocialLoginRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.EmailOtpRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.EmailVerifyRequest
import org.slf4j.LoggerFactory
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
    private val sendOtpUseCase: SendOtpUseCase,
    private val verifyOtpUseCase: VerifyOtpUseCase,
) {
    private val logger = LoggerFactory.getLogger(AuthController::class.java)

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

    @PostMapping("/email/send")
    fun sendEmailOtp(@RequestBody request: EmailOtpRequest): ResponseEntity<String> {
        return try {
            sendOtpUseCase.sendOtp(request.email)
            ResponseEntity.ok("인증번호가 발송되었습니다.")
        } catch (e: Exception) {
            logger.error("이메일 발송 실패: email={}", request.email, e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이메일 발송에 실패했습니다.")
        }
    }

    @PostMapping("/email/verify")
    fun verifyEmailOtp(@RequestBody request: EmailVerifyRequest): ResponseEntity<String> {
        val isValid = verifyOtpUseCase.verifyOtp(request.email, request.otp)
        return if (isValid) {
            ResponseEntity.ok("인증되었습니다.")
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증번호가 일치하지 않습니다.")
        }
    }
}