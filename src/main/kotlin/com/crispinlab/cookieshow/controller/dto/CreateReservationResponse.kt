package com.crispinlab.cookieshow.controller.dto

import java.time.Instant

internal data class CreateReservationResponse(
    val reservationTime: Instant,
    val paymentEndTime: Instant
)
