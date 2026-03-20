package com.lucvs.apex_f1_api.infrastructure.api.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.application.port.out.RateLimitPort
import com.lucvs.apex_f1_api.domain.model.RateLimitStatus
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AiRateLimitInterceptor(
    private val rateLimitPort: RateLimitPort,
    private val loadUserPort: LoadUserPort,
    private val objectMapper: ObjectMapper
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val auth = SecurityContextHolder.getContext().authentication

        if (auth == null || !auth.isAuthenticated) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "인증되지 않은 사용자입니다.")
            return false
        }

        val userId = auth.name.toLongOrNull()
            ?: run {
                response.sendError(HttpStatus.UNAUTHORIZED.value(), "잘못된 사용자 정보입니다.")
                return false
            }

        val user = loadUserPort.loadUserById(userId)
        if (user == null) {
            response.sendError(HttpStatus.UNAUTHORIZED.value(), "유저 정보를 찾을 수 없습니다.")
            return false
        }

        // 상태값 확인
        val clientIp = getClientIp(request)
        val status = rateLimitPort.tryConsume(clientIp, userId, user.tier)

        // 상태값에 따른 에러 메시지 설정
        if (status != RateLimitStatus.ALLOWED) {
            response.status = HttpStatus.TOO_MANY_REQUESTS.value()
            response.contentType = "application/json;charset=UTF-8"

            val errorMessage = when (status) {
                RateLimitStatus.IP_LIMIT_EXCEEDED -> "해당 IP에서 허용된 분당 AI 요청 횟수를 초과하였습니다. 잠시 후 다시 시도해 주세요."
                RateLimitStatus.USER_LIMIT_EXCEEDED -> "일일 질문 가능 횟수를 모두 소진하였습니다. 더 많은 사용을 위하여 멤버십을 업그레이드해 보세요!"
                else -> "요청 횟수를 초과하였습니다."
            }

            val errorResponse = mapOf(
                "error" to "TOO_MANY_REQUESTS",
                "message" to errorMessage
            )
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
            return false
        }

        return true
    }

    private fun getClientIp(request: HttpServletRequest): String {
        val xForwardedFor = request.getHeader("X-Forwarded-For")
        if (!xForwardedFor.isNullOrBlank()) { return xForwardedFor.split(",").first().trim() }
        return request.remoteAddr ?: "unknown_ip"
    }
}