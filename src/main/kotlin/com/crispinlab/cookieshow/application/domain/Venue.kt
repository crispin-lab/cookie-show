package com.crispinlab.cookieshow.application.domain

data class Venue(
    val id: Long,
    val name: String,
    val address: String,
    val capacity: Int,
    val rows: List<Long>
)
