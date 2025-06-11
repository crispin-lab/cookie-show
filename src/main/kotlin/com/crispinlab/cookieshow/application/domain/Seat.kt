package com.crispinlab.cookieshow.application.domain

internal data class Seat(
    val id: Long,
    val venue: Long,
    val row: String,
    val number: Int,
    val isAvailable: Boolean = true
) {
    fun isSeatIsAvailable() = isAvailable
}
