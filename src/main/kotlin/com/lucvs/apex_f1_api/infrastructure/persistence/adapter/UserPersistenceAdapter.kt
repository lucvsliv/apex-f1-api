package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.domain.model.User
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.UserMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.respository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserPersistenceAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : LoadUserPort {

    override fun loadUserById(userId: Long): User? {
        val userEntity = userRepository.findByIdOrNull(userId)

        return userEntity?.let { userMapper.toDomain(it) }
    }

}