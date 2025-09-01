package com.zunza.gongsamo.participant.exception

import com.zunza.gongsamo.common.CustomException
import org.springframework.http.HttpStatus

class AlreadyParticipatedException(
    val postId: Long,
    val userId: Long
) : CustomException(
    MESSAGE.format(postId, userId)
) {
    companion object {
        private const val MESSAGE = "이미 참여 중인 공동구매입니다. POST ID: %d, REQUESTER ID: %d"
    }

    override fun getStatusCode() = HttpStatus.CONFLICT.value()
}
