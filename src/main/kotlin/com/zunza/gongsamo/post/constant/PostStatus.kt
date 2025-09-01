package com.zunza.gongsamo.post.constant

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class PostStatus(
    @JsonValue
    val value: String
) {
    RECRUITING("모집 중"),
    RECRUITMENT_CLOSED("모집 완료"),
    TRANSACTION_COMPLETED("거래 완료"),
    CANCELLED("취소");

    companion object {
        @JsonCreator
        fun from(value: String): PostStatus {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("존재하지 않는 상태 값 입니다.")
        }
    }
}
