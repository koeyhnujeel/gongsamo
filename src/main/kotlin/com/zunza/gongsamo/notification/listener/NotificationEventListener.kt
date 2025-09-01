package com.zunza.gongsamo.notification.listener

import com.zunza.gongsamo.fcm.service.FcmService
import com.zunza.gongsamo.notification.dto.ParticipantCreatedEvent
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class NotificationEventListener(
    private val fcmService: FcmService
) {
    @Async("fcmNotificationExecutor")
    @TransactionalEventListener
    fun handleParticipantCreatedEvent(event: ParticipantCreatedEvent) {
        fcmService.sendNotificationToUser(
            event.hostId,
            event.notificationTitle,
            event.notificationBody
        )
    }
}
