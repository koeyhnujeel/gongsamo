package com.zunza.gongsamo.post.repository

import com.zunza.gongsamo.post.entity.Post
import com.zunza.gongsamo.post.repository.querydsl.CustomPostRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long>, CustomPostRepository {
}
