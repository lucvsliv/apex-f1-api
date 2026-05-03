package com.lucvs.apex_f1_api.infrastructure.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.slf4j.LoggerFactory

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {

    private val log = LoggerFactory.getLogger(javaClass)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1. HTTP header에서 토큰 추출
        val token = resolveToken(request)

        // 2. 토큰 존재 & 유효성 검사
        if (token != null) {
            if (jwtProvider.validateToken(token)) {
                // 3. 토큰에서 user id 추출
                val userId = jwtProvider.getUserIdFromToken(token)
                log.debug("JWT 인증 성공: userId={}", userId)

                // 4. authentication 설정
                val authentication = UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    listOf(SimpleGrantedAuthority("ROLE_USER"))
                )

                // 5. SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                log.warn("JWT 유효성 검사 실패: token={}", token.take(10) + "...")
            }
        } else {
            log.debug("요청에 JWT 토큰이 없음: uri={}", request.requestURI)
        }

        // 6. 다음 필터 or controller로 요청 전달
        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }

}