package com.crispinlab.cookieshow.application.service

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.application.domain.User
import com.crispinlab.cookieshow.application.service.dto.CreateReservationRequest
import com.crispinlab.cookieshow.application.service.extensions.toDomain
import com.crispinlab.cookieshow.application.service.extensions.toEntity
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
    fun reserve(request: CreateReservationRequest) {
        performanceRepository.findByIdOrNull(request.performance)?.let {
            seatRepository.findByIdOrNull(request.seat).toDomain()
            val user: User = request.user.toDomain()
            userRepository.save(user.toEntity())
            val performance: Performance = it.toDomain()
            request.toDomain(user, performance)
        } ?: throw IllegalArgumentException()
    }
}
