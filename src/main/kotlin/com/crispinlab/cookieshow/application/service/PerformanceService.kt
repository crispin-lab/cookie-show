package com.crispinlab.cookieshow.application.service

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.service.extensions.toDomain
import com.crispinlab.cookieshow.application.service.extensions.toDto
import com.crispinlab.cookieshow.application.service.extensions.toEntity
import com.crispinlab.cookieshow.application.usecase.PerformanceRegisterUseCase
import com.crispinlab.cookieshow.repository.PerformanceRepository
import com.crispinlab.cookieshow.repository.VenueRepository
import org.springframework.stereotype.Service

@Service
internal class PerformanceService(
    private val performanceRepository: PerformanceRepository,
    private val venueRepository: VenueRepository
) : PerformanceRegisterUseCase {
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
}
