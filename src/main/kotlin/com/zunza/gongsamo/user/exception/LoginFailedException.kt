package com.zunza.gongsamo.user.exception

import com.zunza.gongsamo.common.CustomException
import jakarta.servlet.http.HttpServletResponse.*

class LoginFailedException : CustomException(MESSAGE) {

    companion object {
        private const val MESSAGE = "아이디 또는 비밀번호를 확인해 주세요."
    }

    override fun getStatusCode() = SC_UNAUTHORIZED
}
