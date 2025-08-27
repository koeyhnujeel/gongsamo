package com.zunza.gongsamo.post.repository

import com.zunza.gongsamo.post.entity.Participant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ParticipantRepository : JpaRepository<Participant, Long>{
}
