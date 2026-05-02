package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.port.`in`.GetUserInfoUseCase
import com.lucvs.apex_f1_api.application.port.`in`.UpdateUserUseCase
import com.lucvs.apex_f1_api.infrastructure.api.dto.UpdateUserRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.UserMeResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val updateUserUseCase: UpdateUserUseCase
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

    @PutMapping("/me")
    fun updateMyInfo(
        authentication: Authentication,
        @RequestBody request: UpdateUserRequest
    ): ResponseEntity<Any> {
        return try {
            val userId = authentication.name.toLong()
            val updatedUser = updateUserUseCase.updateUser(
                userId = userId,
                nickname = request.nickname,
                profileImageUrl = request.profileImageUrl
            )
            ResponseEntity.ok(UserMeResponse.from(updatedUser))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
        }
    }
}