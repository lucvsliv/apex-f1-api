package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.GetUserInfoUseCase
import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val loadUserPort: LoadUserPort
) : GetUserInfoUseCase {

    override fun getCurrentUser(userId: Long): User {
        return loadUserPort.loadUserById(userId)
            ?: throw IllegalArgumentException("해당 ID의 유저를 찾을 수 없습니다: $userId")
    }
}