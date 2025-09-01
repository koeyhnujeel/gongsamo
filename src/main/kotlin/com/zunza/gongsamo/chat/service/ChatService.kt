package com.zunza.gongsamo.chat.service

import com.zunza.gongsamo.chat.entity.ChatRoom
import com.zunza.gongsamo.chat.repository.ChatRoomRepository
import com.zunza.gongsamo.post.entity.Post
import org.springframework.stereotype.Service

@Service
class ChatService(
    val chatRoomRepository: ChatRoomRepository,
) {
    fun createChatRoom(post: Post) {
        chatRoomRepository.save(ChatRoom.createFrom(post))
    }
}
