package com.zunza.gongsamo.user.controller

import com.zunza.gongsamo.common.ApiResponse
import com.zunza.gongsamo.user.dto.EmailAvailableResponse
import com.zunza.gongsamo.user.dto.NicknameAvailableResponse
import com.zunza.gongsamo.user.dto.SignupRequest
import com.zunza.gongsamo.user.service.AuthService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService
) {
    @GetMapping("/api/auth/signup/email/validation")
    fun checkEmailDuplication(
        @RequestParam email: String
    ): ResponseEntity<EmailAvailableResponse> =
        ResponseEntity.ok(authService.isEmailAvailable(email))

    @GetMapping("/api/auth/signup/nickname/validation")
    fun checkNicknameDuplication(
        @RequestParam nickname: String
    ): ResponseEntity<NicknameAvailableResponse> =
        ResponseEntity.ok(authService.isNicknameAvailable(nickname))

    @PostMapping("/api/auth/signup")
    fun signup(
        @Valid @RequestBody signupRequest: SignupRequest
    ): ResponseEntity<ApiResponse<Unit>> {
        authService.signup(signupRequest)
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build()
    }
}
