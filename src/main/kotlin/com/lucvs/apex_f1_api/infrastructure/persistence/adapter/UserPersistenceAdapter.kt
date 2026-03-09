package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.application.port.out.SaveUserPort
import com.lucvs.apex_f1_api.domain.model.User
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.UserMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.respository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : LoadUserPort, SaveUserPort {

    override fun loadUserById(userId: Long): User? {
        val userEntity = userRepository.findByIdOrNull(userId)
        return userEntity?.let { userMapper.toDomain(it) }
    }

    override fun loadUserByEmail(email: String): User? {
        val userEntity = userRepository.findByEmail(email)
        return userEntity?.let { userMapper.toDomain(it) }
    }

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        val savedEntity = userRepository.save(entity)
        return userMapper.toDomain(savedEntity)
    }

    override fun existsByEmail(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    override fun existsByNickname(nickname: String): Boolean {
        return userRepository.existsByNickname(nickname)
    }
}