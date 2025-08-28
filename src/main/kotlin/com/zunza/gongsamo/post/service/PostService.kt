package com.zunza.gongsamo.post.service

import com.zunza.gongsamo.post.constant.SortType
import com.zunza.gongsamo.post.dto.CreatePostRequest
import com.zunza.gongsamo.post.dto.LocationFilter
import com.zunza.gongsamo.post.dto.PostCursor
import com.zunza.gongsamo.post.dto.PostDetailsResponse
import com.zunza.gongsamo.post.dto.PostPageResponse
import com.zunza.gongsamo.post.entity.MeetingLocation
import com.zunza.gongsamo.post.entity.Participant
import com.zunza.gongsamo.post.entity.Post
import com.zunza.gongsamo.post.exception.PostNotFoundException
import com.zunza.gongsamo.post.repository.PostRepository
import com.zunza.gongsamo.user.exception.UserNotFoundException
import com.zunza.gongsamo.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service


@Service
class PostService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
) {
    @Transactional
    fun createPost(
        userId: Long,
        createPostRequest: CreatePostRequest
    ) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException(userId)

        val meetingLocationRequest = requireNotNull(createPostRequest.meetingLocation)
        val meetingLocation = MeetingLocation.createFrom(meetingLocationRequest)
        val post = Post.createOf(user, createPostRequest, meetingLocation)
        val participant = Participant.createOf(post, user, true)
        post.addParticipant(participant)
        postRepository.save(post)
    }

    fun getPostPage(
        locationFilter: LocationFilter,
        sortType: SortType,
        size: Int,
        postCursor: PostCursor
    ): PostPageResponse {
        return postRepository.findPageByCursor(
            locationFilter,
            sortType,
            size,
            postCursor
        )
    }

    fun getPostDetails(
        postId: Long,
        userId: Long?
    ): PostDetailsResponse? {
        return postRepository.findPostDetailsById(postId, userId)
            ?: throw PostNotFoundException(postId)
    }
}
