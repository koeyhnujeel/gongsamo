package com.zunza.gongsamo.post.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.zunza.gongsamo.post.entity.SettlementType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime

data class CreatePostRequest(
    @field: NotBlank(message = "제목을 입력해 주세요.")
    val title: String?,

    @field: NotBlank(message = "상품 설명을 입력해 주세요.")
    val description: String?,

    val productImageUrl: String? = null,

    val productLink: String? = null,

    @field: NotNull(message = "상품 가격을을 입력해 주세요.")
    val productPrice: BigDecimal?,

    @field: NotNull(message = "거래 희망 장소를 입력해 주세요.")
    val meetingLocation: MeetingLocationRequest?,

    @field: NotNull(message = "모집 인원을 설정해 주세요.")
    val maxParticipants: Int?,

    @field: NotNull(message = "모집 기간을 설정해 주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    val recruitmentDeadline: LocalDateTime?,

    @field: NotNull(message = "정산 방법을 선택해 주세요.")
    val settlementType: SettlementType?
)

data class MeetingLocationRequest(
    val placeName: String,
    val addressName: String,
    val x: String,
    val y: String
)
