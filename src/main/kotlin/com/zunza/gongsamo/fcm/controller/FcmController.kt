package com.zunza.gongsamo.fcm.controller

import com.zunza.gongsamo.common.ApiResponse
import com.zunza.gongsamo.fcm.dto.FcmTokenRequest
import com.zunza.gongsamo.fcm.service.FcmService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FcmController(
    private val fcmService: FcmService
) {
    @PostMapping("/api/fcm/tokens")
    fun saveToken(
        @AuthenticationPrincipal userId: Long,
        @RequestBody fcmTokenRequest: FcmTokenRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        fcmService.saveToken(userId, fcmTokenRequest)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }
}
