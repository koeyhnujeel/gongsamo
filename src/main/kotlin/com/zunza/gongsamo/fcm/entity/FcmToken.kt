package com.zunza.gongsamo.fcm.entity

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
class FcmToken(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    var token: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User
) : BaseEntity() {

    companion object {
        fun createOf(token: String, user: User): FcmToken {
            return FcmToken(token = token, user = user)
        }
    }
}
