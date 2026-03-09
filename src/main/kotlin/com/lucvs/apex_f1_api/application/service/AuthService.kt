package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.CheckNicknameUseCase
import com.lucvs.apex_f1_api.application.port.`in`.LoginUseCase
import com.lucvs.apex_f1_api.application.port.`in`.SignUpUseCase
import com.lucvs.apex_f1_api.application.port.`in`.SocialLoginUseCase
import com.lucvs.apex_f1_api.application.port.out.LoadSocialUserPort
import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.application.port.out.SaveUserPort
import com.lucvs.apex_f1_api.domain.model.AuthProvider
import com.lucvs.apex_f1_api.domain.model.User
import com.lucvs.apex_f1_api.infrastructure.api.dto.LoginRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.SignUpRequest
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.UserMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.UserRepository
import com.lucvs.apex_f1_api.infrastructure.security.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val socialLoadPorts: List<LoadSocialUserPort>,
    private val userRepository: UserRepository,
    private val userMapper: UserMapper,
    private val jwtProvider: JwtProvider,
    private val saveUserPort: SaveUserPort,
    private val passwordEncoder: PasswordEncoder,
    private val loadUserPort: LoadUserPort
) : SignUpUseCase, SocialLoginUseCase, LoginUseCase, CheckNicknameUseCase {

    /**
     * 소셜 로그인
     */
    @Transactional
    override fun socialLogin(provider: AuthProvider, accessToken: String): String {
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

    /**
     * 일반 회원가입
     */
    @Transactional
    override fun signUp(request: SignUpRequest) {
        // 1. 중복 검사
        if (loadUserPort.existsByEmail(request.email)) { throw IllegalArgumentException("이미 사용 중인 이메일입니다.") }
        if (loadUserPort.existsByNickname(request.nickname)) { throw IllegalArgumentException("이미 사용 중인 닉네임입니다. ") }

        // 2. 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(request.password)

        // 3. 도메인 객체 생성 (일반 회원가입, provider: LOCAL)
        val newUser = User(
            provider = AuthProvider.LOCAL,
            providerId = request.email,
            email = request.email,
            password = encodedPassword,
            nickname = request.nickname,
            profileImageUrl = request.profileImageUrl
        )

        // 4. DB 저장
        saveUserPort.save(newUser)
    }

    /**
     * 일반 로그인 (로그인)
     */
    @Transactional(readOnly = true)
    override fun login(request: LoginRequest): String {
        // 1. 이메일로 유저 검색
        val user = loadUserPort.loadUserByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.")

        // 2. 소셜 가입 유저 방어 로직
        if (user.password == null) {
            throw IllegalArgumentException("소셜 로그인으로 가입된 계정입니다.")
        }

        // 3. 비밀번호 검증
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("이메일 또는 비밀번호가 올바르지 않습니다.")
        }

        // 4. 검증 성공 시 JWT 토큰 발급
        return jwtProvider.generateAccessToken(user.id!!, user.role.name)
    }

    /**
     * 닉네임 중복 검사
     */
    @Transactional(readOnly = true)
    override fun checkNickname(nickname: String) {
        if (loadUserPort.existsByNickname(nickname)) {
            throw IllegalArgumentException("이미 사용 중인 닉네임입니다.")
        }
    }
}