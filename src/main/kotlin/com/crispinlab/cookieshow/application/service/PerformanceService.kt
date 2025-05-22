package com.crispinlab.cookieshow.application.service

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.service.extensions.toDomain
import com.crispinlab.cookieshow.application.service.extensions.toDto
import com.crispinlab.cookieshow.application.service.extensions.toEntity
import com.crispinlab.cookieshow.application.usecase.PerformanceRegisterUseCase
import com.crispinlab.cookieshow.application.usecase.PerformanceRetrievalUseCase
import com.crispinlab.cookieshow.repository.PerformanceRepository
import com.crispinlab.cookieshow.repository.VenueRepository
import com.crispinlab.cookieshow.util.PageLimitCalculator
import org.springframework.stereotype.Service

@Service
internal class PerformanceService(
    private val performanceRepository: PerformanceRepository,
    private val venueRepository: VenueRepository
) : PerformanceRegisterUseCase,
    PerformanceRetrievalUseCase {
    override fun register(
        request: PerformanceRegisterUseCase.RegisterRequest
    ): PerformanceRegisterUseCase.RegisterResponse {
        require(venueRepository.existsById(request.venue)) {
            throw IllegalArgumentException()
        }

        val performance: Performance = request.toDomain()
        val id: Long = performanceRepository.save(performance.toEntity()).id

        return performance.toDto(id)
    }

    override fun retrieveAll(
        request: PerformanceRetrievalUseCase.RetrieveAllRequest
    ): PerformanceRetrievalUseCase.RetrieveAllResponse {
        val pageLimit: Long =
            PageLimitCalculator.calculatePageLimit(
                page = request.page,
                pageSize = request.pageSize
            )
        val count: Long = performanceRepository.count(pageLimit)
        val performances: List<Performance> =
            performanceRepository
                .findAll(
                    limit = request.pageSize,
                    offset = (request.page - 1) * request.pageSize
                ).map { it.toDomain() }
        return PerformanceRetrievalUseCase.RetrieveAllResponse(
            performances = performances.toDto(),
            count = count
        )
    }
}
