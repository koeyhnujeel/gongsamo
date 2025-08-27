package com.zunza.gongsamo.post.dto

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class PostCursor(
    val postId: Long? = null,

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    @field: DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val datetime: LocalDateTime? = null,
)
