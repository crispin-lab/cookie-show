package com.crispinlab.cookieshow.application.domain

internal data class Seat(
    val id: Long,
    val venue: Long,
    val position: SeatPosition
)
