package com.crispinlab.cookieshow.application.service

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.domain.Seat
import com.crispinlab.cookieshow.application.domain.Venue
import com.crispinlab.cookieshow.application.service.dto.CreatePerformanceRequest
import com.crispinlab.cookieshow.application.service.dto.RetrieveAllPerformancesParams
import com.crispinlab.cookieshow.application.service.extensions.toDomain
import com.crispinlab.cookieshow.application.service.extensions.toDto
import com.crispinlab.cookieshow.application.service.extensions.toEntity
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceResponse
import com.crispinlab.cookieshow.controller.dto.RetrieveAllPerformancesResponse
import com.crispinlab.cookieshow.controller.dto.RetrievePerformanceResponse
import com.crispinlab.cookieshow.repository.PerformanceRepository
import com.crispinlab.cookieshow.repository.SeatRepository
import com.crispinlab.cookieshow.repository.VenueRepository
import com.crispinlab.cookieshow.util.PageLimitCalculator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
internal class PerformanceService(
    private val performanceRepository: PerformanceRepository,
    private val venueRepository: VenueRepository,
    private val seatRepository: SeatRepository
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

        val venueIds: List<Long> = performances.map { it.venue }.distinct()
        val venues: Map<Long, Venue> =
            venueRepository
                .findAllById(venueIds)
                .map {
                    it.toDomain()
                }.associateBy { it.id }

        return RetrieveAllPerformancesResponse(
            performances = performances.toDto(venues),
            count = count
        )
    }

    @Transactional
    fun retrieve(id: Long): RetrievePerformanceResponse =
        performanceRepository.findByIdOrNull(id)?.let {
            val venue: Venue = (
                venueRepository.findByIdOrNull(it.venue)?.toDomain()
                    ?: throw IllegalArgumentException()
            )
            val seats: List<Seat> =
                seatRepository
                    .findAllByVenue(venue.id)
                    .map { seatEntity ->
                        seatEntity.toDomain()
                    }

            val performance: Performance = it.toDomain()

            RetrievePerformanceResponse(
                id = performance.id!!,
                title = performance.title,
                description = performance.description,
                venue = venue.toDto(),
                startTime = performance.startTime,
                endTime = performance.endTime,
                reservationStartTime = performance.reservationStartTime,
                reservationEndTime = performance.reservationEndTime,
                seats = seats.map { seat -> seat.toDto() },
                remainingSeatCount = seats.count { seat -> seat.isAvailable }
            )
        } ?: throw IllegalArgumentException()
}
