package com.lucvs.apex_f1_api.infrastructure.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        // 1. HTTP header에서 토큰 추출
        val token = resolveToken(request)

        // 2. 토큰 존재 & 유효성 검사
        if (token != null && jwtProvider.validateToken(token)) {
            // 3. 토큰에서 user id 추출
            val userId = jwtProvider.getUserIdFromToken(token)

            // 4. authentication 설정
            val authentication = UsernamePasswordAuthenticationToken(
                userId,
                null,
                listOf(SimpleGrantedAuthority("ROLE_USER"))
            )

            // 5. SecurityContext에 인증 정보 저장
            SecurityContextHolder.getContext().authentication = authentication
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