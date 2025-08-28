package com.zunza.gongsamo.post.constant

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class SettlementType(
    @JsonValue
    val value: String
) {
    FACE_TO_FACE("만나서 정산"),
    SAFE_PAYMENT("안전 정산");

    companion object {
        @JsonCreator
        @JvmStatic
        fun from(value: String): SettlementType {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("잘못된 정산 방법입니다.")
        }
    }
}
