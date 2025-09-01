package com.zunza.gongsamo.chat.entity

import com.zunza.gongsamo.common.BaseEntity
import com.zunza.gongsamo.post.entity.Post
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne

@Entity
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, unique = true)
    val post: Post,

    @Column(nullable = false)
    var isActive: Boolean = true
) : BaseEntity() {

    companion object {
        fun createFrom(post: Post): ChatRoom {
            return ChatRoom(post = post)
        }
    }
}
