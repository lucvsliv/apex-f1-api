package com.lucvs.apex_f1_api.infrastructure.sse

import org.springframework.stereotype.Repository
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.ConcurrentHashMap

@Repository
class SseEmitterRepository {
    private val emitters = ConcurrentHashMap<Long, SseEmitter>()

    fun save(userId: Long, emitter: SseEmitter) {
        emitters[userId] = emitter
    }

    fun get(userId: Long): SseEmitter? {
        return emitters[userId]
    }

    fun deleteById(userId: Long) {
        emitters.remove(userId)
    }
}