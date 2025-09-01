package com.zunza.gongsamo.participant.repository

import com.zunza.gongsamo.participant.entity.Participant
import com.zunza.gongsamo.post.entity.Post
import com.zunza.gongsamo.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParticipantRepository : JpaRepository<Participant, Long> {
    fun existsByPostAndUser(post: Post, user: User): Boolean
    fun countByPost(post: Post): Long
    fun findByPostIdAndUserId(postId: Long, userId: Long): Participant?
}
