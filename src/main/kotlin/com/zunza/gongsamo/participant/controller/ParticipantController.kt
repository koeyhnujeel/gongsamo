package com.zunza.gongsamo.participant.controller

import com.zunza.gongsamo.common.ApiResponse
import com.zunza.gongsamo.participant.service.ParticipantService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ParticipantController(
    private val participantService: ParticipantService
) {
    @PostMapping("/api/posts/{postId}/participants")
    fun participate(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<Unit>> {
        participantService.createParticipation(postId, userId)
        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @DeleteMapping("/api/posts/{postId}/participants")
    fun cancelParticipation(
        @PathVariable postId: Long,
        @AuthenticationPrincipal userId: Long
    ): ResponseEntity<ApiResponse<Unit>> {
        participantService.deleteParticipation(postId, userId)
        return ResponseEntity.noContent().build()
    }
}
