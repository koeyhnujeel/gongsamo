package com.zunza.gongsamo.user.service

import com.zunza.gongsamo.user.repository.UserRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class AuthServiceTest : BehaviorSpec ({
    val userRepository = mockk<UserRepository>()
    val authService = AuthService(userRepository)

    Given("회원가입 시 이메일, 닉네임 중복 체크할 때") {
        val email = "test@email.com"
        val nickname = "test"
        When("사용할 수 있는 이메일이라면") {
            every { userRepository.existsByEmail(any()) } returns false
            Then("사용 가능을 응답해야한다.") {
                val response = authService.isEmailAvailable(email)
                response.isAvailable shouldBe true
            }
        }

        When("중복된 이메일이라면") {
            every { userRepository.existsByEmail(any()) } returns true
            Then("사용 불가능을 응답해야한다.") {
                val response = authService.isEmailAvailable(email)
                response.isAvailable shouldBe false
            }
        }

        When("잘못된 이메일 형식이라면") {
            val invalidEmail = "testEmail.com"
            Then("예외를 발생시켜야 한다") {
                shouldThrow<IllegalArgumentException> {
                    authService.isEmailAvailable(invalidEmail)
                }
            }
        }

        When("사용할 수 있는 닉네임이라면") {
            every { userRepository.existsByNickname(any()) } returns false
            Then("사용 가능을 응답해야한다.") {
                val response = authService.isNicknameAvailable(nickname)
                response.isAvailable shouldBe true
            }
        }

        When("중복된 닉네임이라면") {
            every { userRepository.existsByNickname(any()) } returns true
            Then("사용 불가능을 응답해야한다.") {
                val response = authService.isNicknameAvailable(nickname)
                response.isAvailable shouldBe false
            }
        }

        When("잘못된 닉네임 형식이라면") {
            val invalidNickname = "12321"
            Then("예외를 발생시켜야 한다") {
                shouldThrow<IllegalArgumentException> {
                    authService.isNicknameAvailable(invalidNickname)
                }
            }
        }
    }
})
