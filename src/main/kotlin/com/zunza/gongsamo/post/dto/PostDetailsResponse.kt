package com.zunza.gongsamo.post.dto

import com.zunza.gongsamo.post.constant.PostStatus
import com.zunza.gongsamo.post.constant.SettlementType
import java.math.BigDecimal
import java.time.LocalDateTime

data class PostDetailsResponse(
    val id: Long,
    val hostName: String,
    val title: String,
    val description: String,
    val settlementType: SettlementType,
    val postStatus: PostStatus,
    val productImageUrl: String?,
    val productLink: String?,
    val productPrice: BigDecimal,
    val maxParticipants: Int,
    val currentParticipants: Long,
    val recruitmentDeadline: LocalDateTime,
    val meetingPlace: String,
    val x: String,
    val y: String,
    val isHost: Boolean,
    val isParticipant: Boolean,
    val createdAt: LocalDateTime
)
