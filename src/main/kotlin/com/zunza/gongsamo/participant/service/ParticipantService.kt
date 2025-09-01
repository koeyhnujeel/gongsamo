package com.zunza.gongsamo.participant.service

import com.zunza.gongsamo.notification.dto.ParticipantCreatedEvent
import com.zunza.gongsamo.participant.entity.Participant
import com.zunza.gongsamo.participant.exception.AlreadyParticipatedException
import com.zunza.gongsamo.participant.exception.ParticipantNotFoundException
import com.zunza.gongsamo.participant.exception.ParticipationQuotaExceededException
import com.zunza.gongsamo.participant.exception.PostNotRecruitingException
import com.zunza.gongsamo.participant.exception.SelfParticipationNotAllowedException
import com.zunza.gongsamo.participant.repository.ParticipantRepository
import com.zunza.gongsamo.post.constant.PostStatus
import com.zunza.gongsamo.post.exception.PostNotFoundException
import com.zunza.gongsamo.post.repository.PostRepository
import com.zunza.gongsamo.user.exception.UserNotFoundException
import com.zunza.gongsamo.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ParticipantService(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val participantRepository: ParticipantRepository,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    /**
     * TODO 동시성 문제 확인(lock 알아보기) | 알림 보내기
     */
    @Transactional
    fun createParticipation(postId: Long, userId: Long) {
        val post = postRepository.findByIdOrNull(postId)
            ?: throw PostNotFoundException(postId)

        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException(userId)

        // 셀프 참여인가(host 인가)
        if (post.host.id == user.id) {
            throw SelfParticipationNotAllowedException(
                postId,
                post.host.id,
                userId
            )
        }
        // 이미 참여
        if (participantRepository.existsByPostAndUser(post, user)) {
            throw AlreadyParticipatedException(postId, userId)
        }
        // 모집 중인가
        if (post.status != PostStatus.RECRUITING) {
            throw PostNotRecruitingException(postId)
        }

        // 자리 남았는가
        val currentParticipants = participantRepository.countByPost(post)
        if (currentParticipants >= post.maxParticipants) {
            throw ParticipationQuotaExceededException(postId)
        }

        // participant 생성
        participantRepository.save(Participant.createOf(post, user))

        // 알림 발송
        applicationEventPublisher.publishEvent(
            ParticipantCreatedEvent.createOf(
            post.host.id,
            user.nickname,
            post.title
            )
        )
    }

    fun deleteParticipation(postId: Long, userId: Long) {
        val participant = participantRepository.findByPostIdAndUserId(postId, userId)
            ?: throw ParticipantNotFoundException(postId, userId)

        participantRepository.delete(participant)
    }
}
