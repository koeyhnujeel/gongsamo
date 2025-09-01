package com.zunza.gongsamo.participant.exception

import com.zunza.gongsamo.common.CustomException
import org.springframework.http.HttpStatus

class ParticipationQuotaExceededException(
    val postId: Long
) : CustomException(
    MESSAGE.format(postId)
) {
    companion object {
        private const val MESSAGE = "참여 인원이 모두 찼습니다. POST ID: %d"
    }

    override fun getStatusCode() = HttpStatus.CONFLICT.value()
}
