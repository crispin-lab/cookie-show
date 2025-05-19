package com.crispinlab.cookieshow.application.usecase

import java.time.Instant

internal interface PerformanceRegisterUseCase {
    data class RegisterRequest(
        val title: String,
        val description: String,
        val venue: Long,
        val startTime: Instant,
        val endTime: Instant,
        val reservationStartTime: Instant?,
        val reservationEndTime: Instant?
    )

    data class RegisterResponse(
        val id: Long,
        val title: String,
        val description: String,
        val venue: Long,
        val startTime: Instant,
        val endTime: Instant,
        val reservationStartTime: Instant?,
        val reservationEndTime: Instant?
    )

    fun register(request: RegisterRequest): RegisterResponse
}
