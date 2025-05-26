package com.crispinlab.cookieshow.application.service.dto

internal data class CreateReservationRequest(
    val performance: Long,
    val seat: Long,
    val user: ReservationUser
) {
    data class ReservationUser(
        val name: String,
        val phone: String,
        val email: String
    )
}
