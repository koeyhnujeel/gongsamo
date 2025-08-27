package com.zunza.gongsamo.post.dto

import java.math.BigDecimal
import java.time.LocalDateTime

data class PostPageResponse(
    val postPreviews: List<PostPreview> = emptyList(),
    val nextCursor: NextCursor? = null,
    val hasMore: Boolean = false
)

data class PostPreview(
    val id: Long = 0,
    val title: String = "",
    val meetingPlace: String = "",
    val productPrice: BigDecimal = BigDecimal.ZERO,
    val productImageUrl: String? = null,
    val maxParticipants: Int = 0,
    val currentParticipants: Long = 0,
    val recruitmentDeadline: LocalDateTime = LocalDateTime.MIN,
    val createdAt: LocalDateTime = LocalDateTime.MIN
)

data class NextCursor(
    val postId: Long? = null,
    val datetime: LocalDateTime? = null
)
