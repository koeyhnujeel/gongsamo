package com.zunza.gongsamo.participant.exception

import com.zunza.gongsamo.common.CustomException
import org.springframework.http.HttpStatus

class SelfParticipationNotAllowedException(
    postId: Long,
    hostId: Long,
    requesterId: Long
) : CustomException(
    MESSAGE.format(postId, hostId, requesterId)
) {
    companion object {
        private const val MESSAGE = "호스트는 자신의 공동구매에 참가 신청할 수 없습니다. POST ID: %d, HOST ID: %d, REQUESTER ID: %d"
    }

    override fun getStatusCode() = HttpStatus.BAD_REQUEST.value()
}
