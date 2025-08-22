package com.zunza.gongsamo.user.service

import com.zunza.gongsamo.user.dto.EmailAvailableResponse
import com.zunza.gongsamo.user.dto.NicknameAvailableResponse
import com.zunza.gongsamo.user.dto.SignupRequest
import com.zunza.gongsamo.user.entity.User
import com.zunza.gongsamo.user.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {  }

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun isEmailAvailable(email: String): EmailAvailableResponse {
        validateEmailFormat(email)
        return when (userRepository.existsByEmail(email)) {
            true -> EmailAvailableResponse(false)
            false -> EmailAvailableResponse(true)
        }
    }

    fun isNicknameAvailable(nickname: String): NicknameAvailableResponse {
        validateNicknameFormat(nickname)
        return when (userRepository.existsByNickname(nickname)) {
            true -> NicknameAvailableResponse(false)
            false -> NicknameAvailableResponse(true)
        }
    }

    fun signup(signupRequest: SignupRequest) {
        val encodedPassword = passwordEncoder.encode(signupRequest.password)
        userRepository.save(User.of(
            signupRequest.email,
            signupRequest.nickname,
            encodedPassword
        ))
    }

    private fun validateEmailFormat(email: String) {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$")
        if (!emailRegex.matches(email)) {
            logger.warn { "잘못된 이메일 형식입니다: $email" }
            throw IllegalArgumentException("잘못된 이메일 형식입니다.")
        }
    }

    private fun validateNicknameFormat(nickname: String) {
        val nicknameRegex = Regex("^(?=.*[가-힣a-zA-Z])[가-힣a-zA-Z0-9]{2,12}$")
        if (!nicknameRegex.matches(nickname)) {
            logger.warn { "잘못된 닉네임 형식입니다: $nickname" }
            throw IllegalArgumentException("잘못된 닉네임 형식입니다.")
        }
    }
}
