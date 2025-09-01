package com.zunza.gongsamo.participant.exception

import com.zunza.gongsamo.common.CustomException
import org.springframework.http.HttpStatus

class PostNotRecruitingException(
    val postId: Long
) : CustomException(
    MESSAGE.format(postId)
) {
    companion object {
        private const val MESSAGE = "모집이 마감된 공동구매입니다. POST ID: %d"
    }

    override fun getStatusCode() = HttpStatus.BAD_REQUEST.value()
}
