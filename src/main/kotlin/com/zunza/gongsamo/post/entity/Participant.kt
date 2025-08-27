package com.zunza.gongsamo.post.entity

import com.zunza.gongsamo.common.BaseEntity
import com.zunza.gongsamo.user.entity.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Participant(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val post: Post,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    val user: User,

    @Column(nullable = false)
    val isHost: Boolean = false,

    @Column(nullable = false)
    var settlementConfirmed: Boolean = false
) : BaseEntity() {

    companion object {
        fun createOf(post: Post, user: User, isHost: Boolean = false): Participant {
            return Participant(post = post, user = user, isHost = isHost)
        }
    }
}
