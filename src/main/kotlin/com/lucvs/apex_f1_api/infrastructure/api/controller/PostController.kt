package com.lucvs.apex_f1_api.infrastructure.api.controller

import com.lucvs.apex_f1_api.application.port.`in`.CreatePostCommand
import com.lucvs.apex_f1_api.application.port.`in`.CreatePostUseCase
import com.lucvs.apex_f1_api.application.port.`in`.DeletePostUseCase
import com.lucvs.apex_f1_api.application.port.`in`.GetPostsUseCase
import com.lucvs.apex_f1_api.application.port.`in`.UpdatePostCommand
import com.lucvs.apex_f1_api.application.port.`in`.UpdatePostUseCase
import com.lucvs.apex_f1_api.application.port.out.LoadUserPort
import com.lucvs.apex_f1_api.infrastructure.api.dto.CreatePostRequest
import com.lucvs.apex_f1_api.infrastructure.api.dto.PostResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/posts")
class PostController(
        private val createPostUseCase: CreatePostUseCase,
        private val getPostsUseCase: GetPostsUseCase,
        private val deletePostUseCase: DeletePostUseCase,
        private val updatePostUseCase: UpdatePostUseCase,
        private val loadUserPort: LoadUserPort
) {

    @PutMapping("/{id}")
    fun updatePost(
            authentication: Authentication,
            @PathVariable id: Long,
            @RequestBody request: CreatePostRequest
    ): ResponseEntity<Void> {
        val requesterId = authentication.name.toLong()
        return try {
            val command =
                    UpdatePostCommand(
                            id = id,
                            requesterId = requesterId,
                            title = request.title,
                            content = request.content
                    )
            updatePostUseCase.updatePost(command)
            ResponseEntity.ok().build()
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(403).build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletePost(authentication: Authentication, @PathVariable id: Long): ResponseEntity<Void> {
        val requesterId = authentication.name.toLong()
        return try {
            deletePostUseCase.deletePost(id, requesterId)
            ResponseEntity.noContent().build()
        } catch (e: IllegalAccessException) {
            ResponseEntity.status(403).build()
        } catch (e: IllegalArgumentException) {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createPost(
            authentication: Authentication,
            @RequestBody request: CreatePostRequest
    ): ResponseEntity<PostResponse> {
        val authorId = authentication.name.toLong()
        val user = loadUserPort.loadUserById(authorId)
        val authorNickname = user?.nickname ?: "Unknown"
        val authorProfileImageUrl = user?.profileImageUrl

        val command =
                CreatePostCommand(
                        authorId = authorId,
                        title = request.title,
                        content = request.content
                )

        val createdPost = createPostUseCase.createPost(command)
        val response = PostResponse.from(createdPost, authorNickname, authorProfileImageUrl)

        return ResponseEntity.created(java.net.URI.create("/api/v1/posts/${response.id}"))
                .body(response)
    }

    @GetMapping
    fun getPosts(): ResponseEntity<List<PostResponse>> {
        val posts = getPostsUseCase.getAllPosts()
        val response =
                posts.map { post ->
                    val user = loadUserPort.loadUserById(post.authorId)
                    val nickname = user?.nickname ?: "User ${post.authorId}"
                    val profileImageUrl = user?.profileImageUrl
                    PostResponse.from(post, nickname, profileImageUrl)
                }

        return ResponseEntity.ok(response)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): ResponseEntity<PostResponse> {
        val post = getPostsUseCase.getPost(id) ?: return ResponseEntity.notFound().build()

        val user = loadUserPort.loadUserById(post.authorId)
        val nickname = user?.nickname ?: "User ${post.authorId}"
        val profileImageUrl = user?.profileImageUrl
        return ResponseEntity.ok(PostResponse.from(post, nickname, profileImageUrl))
    }
}
