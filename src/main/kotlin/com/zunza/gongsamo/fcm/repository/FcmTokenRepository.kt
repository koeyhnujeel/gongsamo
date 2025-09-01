package com.zunza.gongsamo.fcm.repository

import com.zunza.gongsamo.fcm.entity.FcmToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FcmTokenRepository : JpaRepository<FcmToken, Long> {
    fun findByToken(token: String): FcmToken?
    fun findByUserId(userId: Long): FcmToken?
}
