package com.crispinlab.cookieshow.controller.dto

internal class PerformanceResponse<T> private constructor(
    val code: String,
    val result: T? = null
) {
    companion object {
        fun error(errorCode: String): PerformanceResponse<Unit> = PerformanceResponse(errorCode)

        fun <T> error(
            errorCode: String,
            result: T
        ): PerformanceResponse<T> = PerformanceResponse(errorCode, result)

        fun success(): PerformanceResponse<Unit> = PerformanceResponse("SUCCESS")

        fun <T> success(result: T): PerformanceResponse<T> = PerformanceResponse("SUCCESS", result)
    }
}
