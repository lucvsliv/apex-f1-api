package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.infrastructure.sse.SseEmitterRepository
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter

@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController(
    private val sseEmitterRepository: SseEmitterRepository
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    private val DEFAULT_TIMEOUT = 3 * 60 * 1000L

    /**
     * 클라이언트가 쿠폰 대기열 진입 직후 SSE 연결 요청
     */
    @GetMapping("/subscribe", produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun subscribe(
        @AuthenticationPrincipal userId: Long
    ): SseEmitter {
        log.info("[*] 클라이언트 SSE 연결 요청. UserId: {}", userId)

        val emitter = SseEmitter(DEFAULT_TIMEOUT)
        sseEmitterRepository.save(userId, emitter)

        // 연결 종료, 타임아웃, 에러 발생 시 메모리 Leak 방지를 위한 콜백
        emitter.onCompletion { sseEmitterRepository.deleteById(userId) }
        emitter.onTimeout { sseEmitterRepository.deleteById(userId) }
        emitter.onError { sseEmitterRepository.deleteById(userId) }

        // 처음 연결 시 503 에러 방지를 위한 Dummy 데이터 발송
        try {
            emitter.send(
                SseEmitter.event()
                    .name("CONNECT")
                    .data("Connected successfully. Waiting for coupon result...")
            )
        } catch (e: Exception) {
            sseEmitterRepository.deleteById(userId)
        }

        return emitter
    }
}