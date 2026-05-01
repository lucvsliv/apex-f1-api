package com.lucvs.apex_f1_api.infrastructure.persistence.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class PostEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "author_id", nullable = false)
    val authorId: Long,

    @Column(name = "title", nullable = false)
    var title: String,

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    var content: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    @PreUpdate
    fun preUpdate() {
        updatedAt = LocalDateTime.now()
    }
}
