package com.lucvs.apex_f1_api.application.service

import com.lucvs.apex_f1_api.application.port.`in`.CreatePostCommand
import com.lucvs.apex_f1_api.application.port.`in`.CreatePostUseCase
import com.lucvs.apex_f1_api.application.port.`in`.DeletePostUseCase
import com.lucvs.apex_f1_api.application.port.`in`.GetPostsUseCase
import com.lucvs.apex_f1_api.application.port.`in`.UpdatePostCommand
import com.lucvs.apex_f1_api.application.port.`in`.UpdatePostUseCase
import com.lucvs.apex_f1_api.application.port.out.DeletePostPort
import com.lucvs.apex_f1_api.application.port.out.LoadPostsPort
import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.application.port.out.SavePostPort
import com.lucvs.apex_f1_api.domain.model.Post
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class PostService(
    private val savePostPort: SavePostPort,
    private val loadPostsPort: LoadPostsPort,
    private val deletePostPort: DeletePostPort,
    private val loadUserPort: LoadUserPort
) : CreatePostUseCase, GetPostsUseCase, DeletePostUseCase, UpdatePostUseCase {

    override fun updatePost(command: UpdatePostCommand) {
        val post = loadPostsPort.loadPost(command.id) ?: throw IllegalArgumentException("Post not found")
        if (post.authorId != command.requesterId) {
            throw IllegalAccessException("You are not authorized to update this post")
        }
        
        val updatedPost = post.copy(
            title = command.title,
            content = command.content,
            updatedAt = LocalDateTime.now()
        )
        savePostPort.savePost(updatedPost)
    }

    override fun deletePost(id: Long, requesterId: Long) {
        val post = loadPostsPort.loadPost(id) ?: throw IllegalArgumentException("Post not found")
        if (post.authorId != requesterId) {
            throw IllegalAccessException("You are not authorized to delete this post")
        }
        deletePostPort.deletePost(id)
    }

    override fun createPost(command: CreatePostCommand): Post {
        val post = Post(
            authorId = command.authorId,
            title = command.title,
            content = command.content
        )
        return savePostPort.savePost(post)
    }

    @Transactional(readOnly = true)
    override fun getAllPosts(): List<Post> {
        return loadPostsPort.loadAllPosts()
    }

    @Transactional(readOnly = true)
    override fun getPost(id: Long): Post? {
        return loadPostsPort.loadPost(id)
    }
}
