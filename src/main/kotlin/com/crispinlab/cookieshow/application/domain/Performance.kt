package com.crispinlab.cookieshow.application.domain

import java.time.Instant

internal data class Performance(
    val id: Long? = null,
    val title: String,
    val description: String,
    val venue: Long,
    val startTime: Instant,
    val endTime: Instant,
    val reservationStartTime: Instant,
    val reservationEndTime: Instant
)
