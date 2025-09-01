package com.zunza.gongsamo.fcm.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import com.google.firebase.messaging.WebpushConfig
import com.google.firebase.messaging.WebpushNotification
import com.zunza.gongsamo.fcm.dto.FcmTokenRequest
import com.zunza.gongsamo.fcm.entity.FcmToken
import com.zunza.gongsamo.fcm.exception.FcmTokenNotFoundException
import com.zunza.gongsamo.fcm.repository.FcmTokenRepository
import com.zunza.gongsamo.user.exception.UserNotFoundException
import com.zunza.gongsamo.user.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class FcmService(
    private val fcmTokenRepository: FcmTokenRepository,
    private val firebaseMessaging: FirebaseMessaging,
    private val userRepository: UserRepository
) {
    fun saveToken(userId: Long, fcmTokenRequest: FcmTokenRequest) {
        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException(userId)

        val token = fcmTokenRepository.findByToken(fcmTokenRequest.token)
            ?.apply { this.user = user }
            ?: FcmToken.createOf(fcmTokenRequest.token, user)

        fcmTokenRepository.save(token)
    }

    fun sendNotificationToUser(receiverId: Long, title: String, body: String) {
        val fcmToken = fcmTokenRepository.findByUserId(receiverId)
            ?: throw FcmTokenNotFoundException(receiverId)


        val message = Message.builder()
            .setToken(fcmToken.token)
            .setNotification(
                Notification.builder()
                    .setTitle(title)
                    .setBody(body)
                    .build()
            )
            .setWebpushConfig(
                WebpushConfig.builder()
                    .setNotification(
                        WebpushNotification.builder()
                            .setTitle(title)
                            .setBody(body)
                            .build()
                    )
                    .build()
            )
//            .putAllData()
            .build()

        firebaseMessaging.send(message)
    }
}
