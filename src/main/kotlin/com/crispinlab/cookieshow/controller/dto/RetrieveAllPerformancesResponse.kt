package com.crispinlab.cookieshow.controller.dto

data class RetrieveAllPerformancesResponse(
    val performances: List<RetrievePerformanceResponse>,
    val count: Long
)
