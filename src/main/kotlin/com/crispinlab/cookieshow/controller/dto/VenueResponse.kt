package com.crispinlab.cookieshow.controller.dto

internal data class VenueResponse(
    val id: Long,
    val name: String,
    val address: String,
    val capacity: Int
)
