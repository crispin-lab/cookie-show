package com.crispinlab.cookieshow.controller.dto

import java.time.Instant

internal data class CreatePerformanceResponse(
    val id: Long,
    val title: String,
    val description: String,
    val venue: Long,
    val startTime: Instant,
    val endTime: Instant,
    val reservationStartTime: Instant,
    val reservationEndTime: Instant
)
