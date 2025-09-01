package com.zunza.gongsamo.fcm.exception

import com.zunza.gongsamo.common.CustomException
import org.springframework.http.HttpStatus

class FcmTokenNotFoundException(
    val userId: Long
) : CustomException(
   MESSAGE.format(userId)
) {
    companion object {
        private const val MESSAGE = "FCM 토큰을 찾을 수 없습니다. USER ID: %d"
    }

    override fun getStatusCode() = HttpStatus.NOT_FOUND.value()
}
