package com.crispinlab.cookieshow.application.service.extensions

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.domain.Reservation
import com.crispinlab.cookieshow.application.domain.Seat
import com.crispinlab.cookieshow.application.domain.User
import com.crispinlab.cookieshow.application.domain.Venue
import com.crispinlab.cookieshow.application.service.dto.CreatePerformanceRequest
import com.crispinlab.cookieshow.application.service.dto.CreateReservationRequest
import com.crispinlab.cookieshow.controller.dto.CreatePerformanceResponse
import com.crispinlab.cookieshow.controller.dto.RetrieveAllPerformancesResponse
import com.crispinlab.cookieshow.controller.dto.SeatResponse
import com.crispinlab.cookieshow.controller.dto.VenueResponse
import com.crispinlab.cookieshow.repository.entity.PerformanceEntity
import com.crispinlab.cookieshow.repository.entity.ReservationEntity
import com.crispinlab.cookieshow.repository.entity.SeatEntity
import com.crispinlab.cookieshow.repository.entity.UserEntity
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

internal fun SeatEntity.toDomain(): Seat =
    Seat(
        id = this.id,
        venue = this.venue,
        row = this.row,
        number = this.number,
        isAvailable = this.isAvailable
    )

internal fun Seat.toDto(): SeatResponse =
    SeatResponse(
        id = this.id,
        row = this.row,
        number = this.number,
        isAvailable = this.isAvailable
    )

internal fun List<Performance>.toDto(
    venues: Map<Long, Venue>
): List<RetrieveAllPerformancesResponse.RetrievePerformanceResponse> =
    this.mapNotNull { performance ->
        venues[performance.venue]?.let {
            performance.toDto(it.toDto())
        }
    }

internal fun CreateReservationRequest.ReservationUser.toDomain(): User =
    User(
        name = this.name,
        phone = this.phone,
        email = this.email
    )

internal fun User.toEntity(): UserEntity =
    UserEntity(
        name = this.name,
        phone = this.phone,
        email = this.email
    )

internal fun UserEntity.toDomain(): User =
    User(
        id = this.id,
        name = this.name,
        phone = this.phone,
        email = this.email
    )

internal fun CreateReservationRequest.toDomain(
    user: User,
    performance: Performance,
    seat: Seat
) = Reservation(
    user = user,
    performance = performance,
    seat = seat,
    reservationTime = this.reservationRequestTime,
)

internal fun Reservation.toEntity(): ReservationEntity =
    ReservationEntity(
        user = this.user.id,
        performance = this.performance.id!!,
        seat = this.seat.id,
        reservationTime = this.reservationTime,
        paymentEndTime = this.paymentEndTimeEffective
    )

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
