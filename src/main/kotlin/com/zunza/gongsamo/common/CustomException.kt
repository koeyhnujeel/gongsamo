package com.zunza.gongsamo.common

abstract class CustomException(
    message: String
) : RuntimeException(message) {
    abstract fun getStatusCode(): Int
}
