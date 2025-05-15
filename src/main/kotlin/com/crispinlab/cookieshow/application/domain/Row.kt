package com.crispinlab.cookieshow.application.domain

internal data class Row(
    val id: Long,
    val label: String,
    val seats: List<Long>
)
