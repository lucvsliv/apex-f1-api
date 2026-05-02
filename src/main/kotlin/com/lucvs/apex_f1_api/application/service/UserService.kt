package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.GetUserInfoUseCase
import com.lucvs.apex_f1_api.application.port.`in`.UpdateUserUseCase
import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.application.port.out.SaveUserPort
import com.lucvs.apex_f1_api.domain.model.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val loadUserPort: LoadUserPort,
    private val saveUserPort: SaveUserPort
) : GetUserInfoUseCase, UpdateUserUseCase {

    override fun getCurrentUser(userId: Long): User {
        return loadUserPort.loadUserById(userId)
            ?: throw IllegalArgumentException("해당 ID의 유저를 찾을 수 없습니다: $userId")
    }

    @Transactional
    override fun updateUser(userId: Long, nickname: String?, profileImageUrl: String?): User {
        val user = loadUserPort.loadUserById(userId)
            ?: throw IllegalArgumentException("해당 ID의 유저를 찾을 수 없습니다: $userId")

        // 1. 닉네임 중복 체크 (변경 시에만)
        if (nickname != null && nickname != user.nickname) {
            if (loadUserPort.existsByNickname(nickname)) {
                throw IllegalArgumentException("이미 사용 중인 닉네임입니다.")
            }
        }

        // 2. 도메인 모델 업데이트 (copy 활용)
        val updatedUser = user.copy(
            nickname = nickname ?: user.nickname,
            profileImageUrl = profileImageUrl ?: user.profileImageUrl
        )

        // 3. 저장 및 반환
        return saveUserPort.save(updatedUser)
    }
}