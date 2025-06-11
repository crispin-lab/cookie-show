package com.crispinlab.cookieshow.application.service.dto

import java.time.Instant

internal data class CreateReservationRequest(
    val performance: Long,
    val seat: Long,
    val user: ReservationUser,
    val reservationRequestTime: Instant
) {
    data class ReservationUser(
        val name: String,
        val phone: String,
        val email: String
    )
}
