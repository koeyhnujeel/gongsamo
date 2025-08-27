package com.zunza.gongsamo.post.constant

enum class SortType(
    val value: String
) {
    LATEST("최신순"),
    DEADLINE_ASC("마감 임박순"),
    DEADLINE_DESC("마감 여유순");

    companion object {
        fun from(value: String): SortType {
            return entries.find { it.value == value }
                ?: throw IllegalArgumentException("존재하지 않는 정렬 방식입니다.")
        }
    }
}
