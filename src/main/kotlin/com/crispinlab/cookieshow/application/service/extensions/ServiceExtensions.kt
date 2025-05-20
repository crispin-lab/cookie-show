package com.crispinlab.cookieshow.application.service.extensions

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.usecase.PerformanceRegisterUseCase
import com.crispinlab.cookieshow.application.usecase.PerformanceRetrievalUseCase
import com.crispinlab.cookieshow.repository.entity.PerformanceEntity
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

internal fun PerformanceRegisterUseCase.RegisterRequest.toDomain() =
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

internal fun Performance.toDto(id: Long): PerformanceRegisterUseCase.RegisterResponse =
    PerformanceRegisterUseCase.RegisterResponse(
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

internal fun List<Performance>.toDto(): List<PerformanceRetrievalUseCase.PerformanceResponse> =
    this.map {
        PerformanceRetrievalUseCase.PerformanceResponse(
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
