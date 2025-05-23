package com.crispinlab.cookieshow.application.service

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.service.dto.CreatePerformanceRequest
import com.crispinlab.cookieshow.application.service.dto.RetrieveAllPerformancesParams
import com.crispinlab.cookieshow.application.service.extensions.toDomain
import com.crispinlab.cookieshow.application.service.extensions.toDto
import com.crispinlab.cookieshow.application.service.extensions.toEntity
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceResponse
import com.crispinlab.cookieshow.controller.dto.RetrieveAllPerformancesResponse
import com.crispinlab.cookieshow.repository.PerformanceRepository
import com.crispinlab.cookieshow.repository.VenueRepository
import com.crispinlab.cookieshow.util.PageLimitCalculator
import org.springframework.stereotype.Service

@Service
internal class PerformanceService(
    private val performanceRepository: PerformanceRepository,
    private val venueRepository: VenueRepository
) {
    fun register(request: CreatePerformanceRequest): CreatePerformanceResponse {
        require(venueRepository.existsById(request.venue)) {
            throw IllegalArgumentException()
        }

        val performance: Performance = request.toDomain()
        val id: Long = performanceRepository.save(performance.toEntity()).id

        return performance.toDto(id)
    }

    fun retrieveAll(params: RetrieveAllPerformancesParams): RetrieveAllPerformancesResponse {
        val pageLimit: Long =
            PageLimitCalculator.calculatePageLimit(
                page = params.page,
                pageSize = params.pageSize
            )
        val count: Long = performanceRepository.count(pageLimit)
        val performances: List<Performance> =
            performanceRepository
                .findAll(
                    limit = params.pageSize,
                    offset = (params.page - 1) * params.pageSize
                ).map { it.toDomain() }
        return RetrieveAllPerformancesResponse(
            performances = performances.toDto(),
            count = count
        )
    }
}
