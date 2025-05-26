package com.crispinlab.cookieshow.controller.dto

internal data class SeatResponse(
    val id: Long,
    val row: String,
    val number: Int,
    val isAvailable: Boolean
)
