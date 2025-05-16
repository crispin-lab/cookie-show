package com.crispinlab.cookieshow.application.usecase

internal interface PerformanceRegisterUseCase {
    data class RegisterRequest(
        val title: String,
        val description: String,
        val venue: Long
    )

    data class RegisterResponse(
        val id: Long,
        val title: String,
        val description: String,
        val venue: Long
    )

    fun register(request: RegisterRequest): RegisterResponse
}
