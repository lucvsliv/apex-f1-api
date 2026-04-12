package com.lucvs.apex_f1_api.infrastructure.persistence.entity.admin

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "dead_letters")
class DeadLetterEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(nullable = false)
    val topic: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val payload: String,

    @Column(nullable = false, length = 500)
    val errorMessage: String,

    @Column(nullable = false)
    var status: String = "PENDING", // PENDING(대기), RETRIED(재처리됨), IGNORED(무시됨)

    val createdAt: LocalDateTime = LocalDateTime.now(),
    var retriedAt: LocalDateTime? = null
) {
    fun markAsRetried() {
        this.status = "RETRIED"
        this.retriedAt = LocalDateTime.now()
    }
}