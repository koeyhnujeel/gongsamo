package com.zunza.gongsamo.participant.exception

import com.zunza.gongsamo.common.CustomException
import org.springframework.http.HttpStatus

class ParticipantNotFoundException(
    val postId: Long,
    val requesterId: Long
) : CustomException(
    MESSAGE.format(postId, requesterId)
) {
    companion object {
        private const val MESSAGE = "참여 정보를 찾을 수 없습니다. POST ID: %d, REQUESTER ID: %d"
    }

    override fun getStatusCode() = HttpStatus.NOT_FOUND.value()
}
