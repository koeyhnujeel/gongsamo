package com.zunza.gongsamo.user.dto

data class LoginResponse(
    val nickname: String,
    val jwt: String
)
