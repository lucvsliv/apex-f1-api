package com.lucvs.apex_f1_api.infrastructure.persistence.adapter

import com.lucvs.apex_f1_api.application.port.out.DeletePostPort
import com.lucvs.apex_f1_api.application.port.out.LoadPostsPort
import com.lucvs.apex_f1_api.application.port.out.SavePostPort
import com.lucvs.apex_f1_api.domain.model.Post
import com.lucvs.apex_f1_api.infrastructure.persistence.mapper.PostMapper
import com.lucvs.apex_f1_api.infrastructure.persistence.repository.PostJpaRepository
import org.springframework.stereotype.Component

@Component
class PostPersistenceAdapter(
    private val postJpaRepository: PostJpaRepository,
    private val postMapper: PostMapper
) : SavePostPort, LoadPostsPort, DeletePostPort {

    override fun deletePost(id: Long) {
        postJpaRepository.deleteById(id)
    }

    override fun savePost(post: Post): Post {
        val entity = postMapper.toEntity(post)
        val savedEntity = postJpaRepository.save(entity)
        return postMapper.toDomain(savedEntity)
    }

    override fun loadAllPosts(): List<Post> {
        return postJpaRepository.findAll(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "createdAt"))
            .map { postMapper.toDomain(it) }
    }

    override fun loadPost(id: Long): Post? {
        return postJpaRepository.findById(id).orElse(null)?.let { postMapper.toDomain(it) }
    }
}
