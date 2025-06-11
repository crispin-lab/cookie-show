package com.crispinlab.cookieshow.application.domain

import java.time.Duration
import java.time.Instant

internal data class Reservation(
    val id: Long = 0L,
    val user: User,
    val seat: Seat,
    val performance: Performance,
    val reservationTime: Instant,
    private val paymentEndTime: Instant? = null
) {
    val paymentEndTimeEffective: Instant
        get() = paymentEndTime ?: reservationTime.plus(Duration.ofHours(12))
}
