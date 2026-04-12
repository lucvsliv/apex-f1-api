package com.lucvs.apex_f1_api.infrastructure.sse

import com.lucvs.apex_f1_api.application.port.out.NotifyCouponResultPort
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SseNotificationAdapter(
    private val sseEmitterRepository: SseEmitterRepository
) : NotifyCouponResultPort {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun notifySuccess(userId: Long, couponPolicyId: Long) {
        sendToClient(userId, "SUCCESS", "쿠폰 발급에 성공했습니다!")
    }

    override fun notifySoldOut(userId: Long, couponPolicyId: Long) {
        sendToClient(userId, "SOLD_OUT", "아쉽게도 선착순 쿠폰이 모두 소진되었습니다.")
    }

    private fun sendToClient(userId: Long, eventName: String, message: String) {
        val emitter = sseEmitterRepository.get(userId)

        if (emitter != null) {
            try {
                // FE로 이벤트 전송
                emitter.send(
                    org.springframework.web.servlet.mvc.method.annotation.SseEmitter.event()
                        .name(eventName)
                        .data(message)
                )
                // 알림을 보냈으니 연결 종료
                emitter.complete()
            } catch (e: Exception) {
                log.error("[!] SSE 알림 전송 실패. UserId: {}", userId, e)
                emitter.completeWithError(e)
            } finally {
                // 저장소에서 제거
                sseEmitterRepository.deleteById(userId)
            }
        } else {
            // 브라우저 탭을 닫았거나 네트워크가 끊겨 Emitter가 없는 경우
            log.warn("[!] 접속 중인 SSE 연결을 찾을 수 없습니다. (알림 유실) UserId: {}", userId)
        }
    }
}