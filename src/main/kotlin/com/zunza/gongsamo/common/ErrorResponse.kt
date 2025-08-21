package com.zunza.gongsamo.common

import com.fasterxml.jackson.annotation.JsonInclude

data class ErrorResponse(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val message: String? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    val messages: List<String?>? = null
)
