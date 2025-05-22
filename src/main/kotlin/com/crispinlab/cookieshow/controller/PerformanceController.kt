package com.crispinlab.cookieshow.controller

import com.crispinlab.cookieshow.application.service.PerformanceService
import com.crispinlab.cookieshow.application.usecase.PerformanceRegisterUseCase
import com.crispinlab.cookieshow.application.usecase.PerformanceRetrievalUseCase
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceRequest
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceResponse
import com.crispinlab.cookieshow.controller.dto.PerformanceResponse
import com.crispinlab.cookieshow.controller.dto.RetrieveAllPerformancesParams
import com.crispinlab.cookieshow.controller.dto.RetrieveAllPerformancesResponse
import com.crispinlab.cookieshow.controller.dto.RetrievePerformanceResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
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

    @GetMapping("/performances")
    fun retrieveAllPerformances(
        @ModelAttribute params: RetrieveAllPerformancesParams
    ): PerformanceResponse<RetrieveAllPerformancesResponse> {
        val response: PerformanceRetrievalUseCase.RetrieveAllResponse =
            performanceService.retrieveAll(
                request =
                    PerformanceRetrievalUseCase.RetrieveAllRequest(
                        page = params.page,
                        pageSize = params.pageSize
                    )
            )
        return PerformanceResponse.success(
            result =
                RetrieveAllPerformancesResponse(
                    performances =
                        response.performances.map {
                            RetrievePerformanceResponse(
                                id = it.id,
                                title = it.title,
                                description = it.description,
                                venue = it.venue,
                                startTime = it.startTime,
                                endTime = it.endTime,
                                reservationStartTime = it.reservationStartTime,
                                reservationEndTime = it.reservationEndTime
                            )
                        },
                    count = response.count
                )
        )
    }
}
