package com.crispinlab.cookieshow.application.service.extensions

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.service.dto.CreatePerformanceRequest
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceResponse
import com.crispinlab.cookieshow.controller.dto.RetrievePerformanceResponse
import com.crispinlab.cookieshow.repository.entity.PerformanceEntity
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

internal fun List<Performance>.toDto(): List<RetrievePerformanceResponse> =
    this.map {
        RetrievePerformanceResponse(
            id = it.id!!,
            title = it.title,
            description = it.description,
            venue = it.venue,
            startTime = it.startTime,
            endTime = it.endTime,
            reservationStartTime = it.reservationStartTime,
            reservationEndTime = it.reservationEndTime
        )
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
