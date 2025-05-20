package com.crispinlab.cookieshow.application.usecase

import java.time.Instant

interface PerformanceRetrievalUseCase {
    data class RetrieveAllRequest(
        val page: Long,
        val pageSize: Long
    )

    data class RetrieveAllResponse(
        val performances: List<PerformanceResponse>,
        val count: Long
    )

    data class PerformanceResponse(
        val id: Long,
        val title: String,
        val description: String,
        val venue: Long,
        val startTime: Instant,
        val endTime: Instant,
        val reservationStartTime: Instant?,
        val reservationEndTime: Instant?
    )

    fun retrieveAll(request: RetrieveAllRequest): RetrieveAllResponse
}
