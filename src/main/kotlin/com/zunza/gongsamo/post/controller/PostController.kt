package com.zunza.gongsamo.post.controller

import com.zunza.gongsamo.common.ApiResponse
import com.zunza.gongsamo.post.constant.SortType
import com.zunza.gongsamo.post.dto.CreatePostRequest
import com.zunza.gongsamo.post.dto.LocationFilter
import com.zunza.gongsamo.post.dto.PostCursor
import com.zunza.gongsamo.post.dto.PostPageResponse
import com.zunza.gongsamo.post.service.PostService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime

@RestController
class PostController(
    private val postService: PostService
) {
    @PostMapping("/api/posts")
    fun createPost(
        @AuthenticationPrincipal userId: Long,
        @Valid @RequestBody createPostRequest: CreatePostRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        postService.createPost(userId, createPostRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/api/posts")
    fun getPostPage(
        @ModelAttribute locationFilter: LocationFilter,
        @RequestParam sortType: SortType = SortType.LATEST,
        @RequestParam size: Int = 15,
        @ModelAttribute postCursor: PostCursor
    ): ResponseEntity<PostPageResponse> {
        return ResponseEntity.ok(postService.getPostPage(
            locationFilter,
            sortType,
            size,
            postCursor
            ))
    }
}
