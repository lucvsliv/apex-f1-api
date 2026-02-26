package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.out.LoadSocialUserPort
import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.domain.model.User
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.UserMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.respository.UserRepository
import com.lucvs.apex_f1_api.infrastructure.security.JwtProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val socialLoadPorts: List<LoadSocialUserPort>,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val jwtProvider: JwtProvider
) {

    @Transactional
    fun socialLogin(provider: AuthProvider, accessToken: String): String {
        val loader = socialLoadPorts.find { it.supports(provider) }
            ?: throw IllegalArgumentException("지원하지 않는 소셜 로그인입니다: $provider")

        val socialUser = loader.loadUser(accessToken)

        val existingEntity = userRepository.findByProviderAndProviderId(socialUser.provider, socialUser.providerId)

        val userEntity = if (existingEntity != null) {
            existingEntity
        } else {
            val newUser = User(
                provider = socialUser.provider,
                providerId = socialUser.providerId,
                email = socialUser.email,
                nickname = socialUser.nickname
            )
            userRepository.save(userMapper.toEntity(newUser))
        }

        val domainUser = userMapper.toDomain(userEntity)

        return jwtProvider.generateAccessToken(domainUser.id!!, domainUser.role.name)
    }
}