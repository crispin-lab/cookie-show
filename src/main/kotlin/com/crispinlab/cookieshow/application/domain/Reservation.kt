package com.crispinlab.cookieshow.application.domain

import java.time.Duration
import java.time.Instant

internal data class Reservation(
    val id: Long = 0L,
    val user: User,
    val seat: Seat,
    val performance: Performance,
    val reservationTime: Instant = Instant.now(),
    private val paymentEndTime: Instant?
) {
    val paymentEndTimeEffective: Instant
        get() = paymentEndTime ?: reservationTime.plus(Duration.ofHours(12))
}
