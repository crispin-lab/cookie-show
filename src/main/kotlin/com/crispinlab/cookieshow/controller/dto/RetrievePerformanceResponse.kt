package com.crispinlab.cookieshow.controller.dto

import java.time.Instant

data class RetrievePerformanceResponse(
    val id: Long,
    val title: String,
    val description: String,
    val venue: Long,
    val startTime: Instant,
    val endTime: Instant,
    val reservationStartTime: Instant,
    val reservationEndTime: Instant
)
