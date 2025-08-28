package com.zunza.gongsamo.post.entity

import com.zunza.gongsamo.common.BaseEntity
import com.zunza.gongsamo.participant.entity.Participant
import com.zunza.gongsamo.post.constant.PostStatus
import com.zunza.gongsamo.post.constant.SettlementType
import com.zunza.gongsamo.post.dto.CreatePostRequest
import com.zunza.gongsamo.post.dto.MeetingLocationRequest
import com.zunza.gongsamo.user.entity.User
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.Lob
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id", nullable = false)
    val host: User,

    @Column(nullable = false, length = 200)
    var title: String,

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    var description: String,

    @Column(length = 500)
    var productImageUrl: String? = null,

    @Column(length = 1000)
    var productLink: String? = null,

    val productPrice: BigDecimal,

    @Embedded
    val meetingLocation: MeetingLocation,

    @Column(nullable = false)
    val maxParticipants: Int,

    @Column(nullable = false)
    val recruitmentDeadline: LocalDateTime,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val settlementType: SettlementType,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    val status: PostStatus,

    @OneToMany(
        mappedBy = "post",
        cascade = [CascadeType.ALL],
        orphanRemoval = true)
    val participants: MutableList<Participant> = mutableListOf(),
) : BaseEntity() {

    companion object {
        fun createOf(
            user: User,
            createPostRequest: CreatePostRequest,
            meetingLocation: MeetingLocation
        ): Post {
            return Post(
                host = user,
                title = createPostRequest.title!!,
                description = createPostRequest.description!!,
                productImageUrl = createPostRequest.productImageUrl,
                productLink = createPostRequest.productLink,
                productPrice = createPostRequest.productPrice!!,
                meetingLocation = meetingLocation,
                maxParticipants = createPostRequest.maxParticipants!!,
                recruitmentDeadline = createPostRequest.recruitmentDeadline!!,
                settlementType = createPostRequest.settlementType!!,
                status = PostStatus.RECRUITING
            )
        }
    }

    fun addParticipant(participant: Participant) {
        this.participants.add(participant)
    }
}

@Embeddable
class MeetingLocation(
    @Column(length = 50)
    val placeName: String,
    @Column(length = 8)
    val sido: String,
    @Column(length = 8)
    val sigungu: String,
    @Column(length = 8)
    val dong: String,
    @Column(length = 30)
    val x: String,
    @Column(length = 30)
    val y: String
) {
    companion object {
        fun createFrom(
            request: MeetingLocationRequest
        ): MeetingLocation {
            val (sido, sigungu, dong) =
                request.addressName
                    .split(" ")
                    .let { parts ->
                        Triple(
                            parts.getOrNull(0) ?: "",
                            parts.getOrNull(1) ?: "",
                            parts.getOrNull(2) ?: ""
                        )
                    }

            return MeetingLocation(
                request.placeName,
                sido,
                sigungu,
                dong,
                request.x,
                request.y
            )
        }
    }
}
