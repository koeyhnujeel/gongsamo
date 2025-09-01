package com.zunza.gongsamo.chat.listener

import com.zunza.gongsamo.chat.dto.PostCreatedEvent
import com.zunza.gongsamo.chat.service.ChatService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ChatEventListener(
    val chatService: ChatService
) {

    @Async("chatCreationExecutor")
    @TransactionalEventListener
    fun handlePostCreatedEvent(event: PostCreatedEvent) {
        chatService.createChatRoom(event.post)
    }
}
