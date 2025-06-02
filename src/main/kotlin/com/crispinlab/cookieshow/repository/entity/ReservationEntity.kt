package com.crispinlab.cookieshow.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "reservations")
internal class ReservationEntity(
    @Column(
        name = "user_id",
        nullable = false
    )
    val user: Long,
    @Column(
        name = "performance_id",
        nullable = false
    )
    val performance: Long,
    @Column(
        name = "seat_id",
        nullable = false
    )
    val seat: Long,
    @Column(nullable = false)
    val reservationTime: Instant,
    @Column(nullable = false)
    val paymentEndTime: Instant
) : BaseEntity()
