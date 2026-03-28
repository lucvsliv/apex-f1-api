package com.lucvs.apex_f1_api.infrastructure.ai.memory

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.ai.chat.memory.ChatMemory
import org.springframework.ai.chat.messages.AssistantMessage
import org.springframework.ai.chat.messages.Message
import org.springframework.ai.chat.messages.MessageType
import org.springframework.ai.chat.messages.SystemMessage
import org.springframework.ai.chat.messages.UserMessage
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class RedisChatMemory(
    private val redisTemplate: StringRedisTemplate,
    private val objectMapper: ObjectMapper
) : ChatMemory {

    private val keyPrefix = "ai:chat:memory"
    private val ttl = Duration.ofHours(24)
    private val maxStoredMessages = 100

    data class MessageDto(val type: String, val content: String)

    override fun add(conversationId: String, messages: List<Message>) {
        val key = "$keyPrefix$conversationId"

        // Spring AI Message -> DTO 변환
        val dtos = messages.map {
            MessageDto(it.messageType.name, it.text ?: "")
        }

        // 기존 대화 내역 연결
        val existing = getDtos(key).toMutableList()
        existing.addAll(dtos)

        // 대화 저장 개수 최적화
        val optimizedList = if (existing.size > maxStoredMessages) {
            existing.takeLast(maxStoredMessages)
        } else {
            existing
        }

        // Redis 덮어쓰기 및 TTL 갱신
        redisTemplate.opsForValue().set(key, objectMapper.writeValueAsString(optimizedList), ttl)
    }

    override fun get(conversationId: String): List<Message> {
        val key = "$keyPrefix$conversationId"
        val dtos = getDtos(key)

        // DTO -> Spring AI Message 변환
        return dtos.map { dto ->
            when (dto.type) {
                MessageType.USER.name -> UserMessage(dto.content)
                MessageType.ASSISTANT.name -> AssistantMessage(dto.content)
                MessageType.SYSTEM.name -> SystemMessage(dto.content)
                else -> UserMessage(dto.content)
            }
        }
    }

    override fun clear(conversationId: String) {
        redisTemplate.delete("$keyPrefix$conversationId")
    }

    private fun getDtos(key: String): List<MessageDto> {
        val json = redisTemplate.opsForValue().get(key) ?: return emptyList()
        val typeFactory = objectMapper.typeFactory
        return objectMapper.readValue(
            json,
            typeFactory.constructCollectionType(List::class.java, MessageDto::class.java)
        )
    }
}