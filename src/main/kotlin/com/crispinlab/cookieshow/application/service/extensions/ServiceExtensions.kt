package com.crispinlab.cookieshow.application.service.extensions

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.domain.Venue
import com.crispinlab.cookieshow.application.service.dto.CreatePerformanceRequest
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceResponse
import com.crispinlab.cookieshow.controller.dto.RetrieveAllPerformancesResponse
import com.crispinlab.cookieshow.controller.dto.VenueResponse
import com.crispinlab.cookieshow.repository.entity.PerformanceEntity
import com.crispinlab.cookieshow.repository.entity.VenueEntity
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

internal fun CreatePerformanceRequest.toDomain() =
    Performance(
        title = this.title,
        description = this.description,
        venue = this.venue,
        startTime = this.startTime,
        endTime = this.endTime,
        reservationStartTime =
            this.reservationStartTime
                ?: defaultReservationStartTime(this.startTime),
        reservationEndTime =
            this.reservationEndTime
                ?: defaultReservationEndTime(this.startTime)
    )

internal fun Performance.toEntity(): PerformanceEntity =
    PerformanceEntity(
        title = this.title,
        description = this.description,
        venue = this.venue,
        startTime = this.startTime,
        endTime = this.endTime,
        reservationStartTime = this.reservationStartTime,
        reservationEndTime = this.reservationEndTime
    )

internal fun Performance.toDto(id: Long): CreatePerformanceResponse =
    CreatePerformanceResponse(
        id = id,
        title = this.title,
        description = this.description,
        venue = this.venue,
        startTime = this.startTime,
        endTime = this.endTime,
        reservationStartTime = this.reservationStartTime,
        reservationEndTime = this.reservationEndTime
    )

internal fun PerformanceEntity.toDomain(): Performance =
    Performance(
        id = this.id,
        title = this.title,
        description = this.description,
        venue = this.venue,
        startTime = this.startTime,
        endTime = this.endTime,
        reservationStartTime = this.reservationStartTime,
        reservationEndTime = this.reservationEndTime
    )

internal fun Venue.toDto(): VenueResponse =
    VenueResponse(
        id = this.id,
        name = this.name,
        address = this.address,
        capacity = this.capacity
    )

internal fun Performance.toDto(
    venue: VenueResponse
): RetrieveAllPerformancesResponse.RetrievePerformanceResponse =
    RetrieveAllPerformancesResponse.RetrievePerformanceResponse(
        id = this.id!!,
        title = this.title,
        description = this.description,
        venue = venue,
        startTime = this.startTime,
        endTime = this.endTime,
        reservationStartTime = this.reservationStartTime,
        reservationEndTime = this.reservationEndTime
    )

internal fun VenueEntity.toDomain(): Venue =
    Venue(
        id = this.id,
        name = this.name,
        address = this.address,
        capacity = this.capacity
    )

internal fun List<Performance>.toDto(
    venues: Map<Long, Venue>
): List<RetrieveAllPerformancesResponse.RetrievePerformanceResponse> =
    this.mapNotNull { performance ->
        venues[performance.venue]?.let {
            performance.toDto(it.toDto())
        }
    }

private fun defaultReservationStartTime(startTime: Instant): Instant =
    startTime
        .atZone(ZoneId.of("Asia/Seoul"))
        .run {
            toLocalDate()
                .minusDays(7)
                .atStartOfDay(ZoneId.of("Asia/Seoul"))
                .toInstant()
        }

private fun defaultReservationEndTime(startTime: Instant): Instant =
    startTime.minus(Duration.ofHours(1))
