package com.zunza.gongsamo.security.jwt

private const val TOKEN_PREFIX = "Bearer "

fun String?.extractJwtFromBearerHeader(): String? =
    this?.takeIf { it.startsWith(TOKEN_PREFIX) }
        ?.substring(TOKEN_PREFIX.length)
