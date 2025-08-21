package com.zunza.gongsamo.user.exception

import com.zunza.gongsamo.common.CustomException
import org.springframework.http.HttpStatus

class UserNotFoundException(
    id: Long
) : CustomException(MESSAGE + id) {

    companion object {
        private const val MESSAGE = "사용자를 찾을 수 업습니다: "
    }

    override fun getStatusCode() =
        HttpStatus.NOT_FOUND.value()
}
