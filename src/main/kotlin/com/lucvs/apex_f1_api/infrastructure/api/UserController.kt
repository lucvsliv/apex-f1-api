package com.lucvs.apex_f1_api.infrastructure.api

import com.lucvs.apex_f1_api.application.port.`in`.GetUserInfoUseCase
import com.lucvs.apex_f1_api.infrastructure.api.dto.UserMeResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val getUserInfoUseCase: GetUserInfoUseCase
) {

    @GetMapping("/me")
    fun getMyInfo(
        authentication: Authentication
    ): ResponseEntity<UserMeResponse> {

        val userId = authentication.name.toLong()
        val userDomain = getUserInfoUseCase.getCurrentUser(userId)
        val response = UserMeResponse.from(userDomain)

        return ResponseEntity.ok(response)
    }
}