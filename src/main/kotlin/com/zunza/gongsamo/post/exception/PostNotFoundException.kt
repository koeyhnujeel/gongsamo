package com.zunza.gongsamo.post.exception

import com.zunza.gongsamo.common.CustomException
import org.springframework.http.HttpStatus

class PostNotFoundException(
    id: Long
) : CustomException(MESSAGE + id) {

    companion object {
        private const val MESSAGE = "게시글을 찾을 수 없습니다: "
    }

    override fun getStatusCode() =
        HttpStatus.NOT_FOUND.value()
}
