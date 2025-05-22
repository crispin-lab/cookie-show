package com.crispinlab.cookieshow.controller

import com.crispinlab.cookieshow.application.service.PerformanceService
import com.crispinlab.cookieshow.application.usecase.PerformanceRegisterUseCase
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceRequest
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceResponse
import com.crispinlab.cookieshow.controller.dto.PerformanceResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
internal class PerformanceController(
    private val performanceService: PerformanceService
) {
    @PostMapping("/performance")
    fun createPerformance(
        @RequestBody request: CreatePerformanceRequest
    ): PerformanceResponse<CreatePerformanceResponse> {
        val response: PerformanceRegisterUseCase.RegisterResponse =
            performanceService.register(
                PerformanceRegisterUseCase.RegisterRequest(
                    title = request.title,
                    description = request.description,
                    venue = request.venue,
                    startTime = request.startTime,
                    endTime = request.endTime,
                    reservationStartTime = request.reservationStartTime,
                    reservationEndTime = request.reservationEndTime
                )
            )
        return PerformanceResponse.success(
            result =
                CreatePerformanceResponse(
                    id = response.id,
                    title = response.title,
                    description = response.description,
                    venue = response.venue,
                    startTime = response.startTime,
                    endTime = response.endTime,
                    reservationStartTime = response.reservationStartTime,
                    reservationEndTime = response.reservationEndTime
                )
        )
    }
}
