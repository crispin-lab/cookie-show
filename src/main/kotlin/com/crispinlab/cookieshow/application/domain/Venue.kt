package com.crispinlab.cookieshow.application.domain

internal data class Venue(
    val id: Long,
    val name: String,
    val address: String,
    val capacity: Int,
    val rows: List<Long>
)
