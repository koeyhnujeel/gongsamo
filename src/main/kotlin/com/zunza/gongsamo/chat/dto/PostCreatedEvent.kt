package com.zunza.gongsamo.chat.dto

import com.zunza.gongsamo.post.entity.Post

data class PostCreatedEvent(
    val post: Post
)
