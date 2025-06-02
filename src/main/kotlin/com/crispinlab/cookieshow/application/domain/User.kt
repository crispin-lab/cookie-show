package com.crispinlab.cookieshow.application.domain

internal data class User(
    val id: Long = 0L,
    val name: String,
    val phone: String,
    val email: String
)
