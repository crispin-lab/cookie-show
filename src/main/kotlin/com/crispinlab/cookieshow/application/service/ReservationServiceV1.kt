package com.crispinlab.cookieshow.application.service

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.domain.Reservation
import com.crispinlab.cookieshow.application.domain.Seat
import com.crispinlab.cookieshow.application.domain.User
import com.crispinlab.cookieshow.application.service.dto.CreateReservationRequest
import com.crispinlab.cookieshow.application.service.extensions.toDomain
import com.crispinlab.cookieshow.application.service.extensions.toEntity
import com.crispinlab.cookieshow.controller.dto.CreateReservationResponse
import com.crispinlab.cookieshow.repository.PerformanceRepository
import com.crispinlab.cookieshow.repository.ReservationRepository
import com.crispinlab.cookieshow.repository.SeatRepository
import com.crispinlab.cookieshow.repository.UserRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
internal class ReservationServiceV1(
    private val userRepository: UserRepository,
    private val performanceRepository: PerformanceRepository,
    private val seatRepository: SeatRepository,
    private val reservationRepository: ReservationRepository
) {
    fun reserve(request: CreateReservationRequest): CreateReservationResponse {
        val seat: Seat =
            seatRepository.findByIdOrNull(request.seat)?.toDomain()
                ?: throw IllegalArgumentException()

        require(seat.isAvailable) {
            throw IllegalArgumentException()
        }

        val performance: Performance =
            performanceRepository.findByIdOrNull(request.performance)?.toDomain()
                ?: throw IllegalArgumentException()

        val user: User = request.user.toDomain()
        userRepository.save(user.toEntity())

        val reservation =
            Reservation(
                user = user,
                seat = seat,
                performance = performance,
                paymentEndTime = request.paymentEndTime
            )

        reservationRepository.save(reservation.toEntity())
        return CreateReservationResponse(
            reservationTime = reservation.reservationTime,
            paymentEndTime = reservation.paymentEndTimeEffective
        )
    }
}
