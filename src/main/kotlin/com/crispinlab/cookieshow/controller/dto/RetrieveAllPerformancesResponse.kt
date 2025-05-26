package com.crispinlab.cookieshow.controller.dto

import java.time.Instant

internal data class RetrieveAllPerformancesResponse(
    val performances: List<RetrievePerformanceResponse>,
    val count: Long
) {
    data class RetrievePerformanceResponse(
        val id: Long,
        val title: String,
        val description: String,
        val venue: VenueResponse,
        val startTime: Instant,
        val endTime: Instant,
        val reservationStartTime: Instant,
        val reservationEndTime: Instant
    )
}
