package com.zunza.gongsamo.notification.dto

class ParticipantCreatedEvent(
    val hostId: Long,
    val participantNickname: String,
    val postTitle: String
) {
    val notificationTitle: String
        get() = "새로운 참여자가 등장했어요!"

    val notificationBody: String
        get() = "${participantNickname}님이 [${postTitle}] 공동구매에 참여했습니다!"

    companion object {
        fun createOf(
            hostId: Long,
            participantNickname: String,
            postTitle: String
        ): ParticipantCreatedEvent {
            return ParticipantCreatedEvent(
                hostId,
                participantNickname,
                postTitle
            )
        }
    }
}
