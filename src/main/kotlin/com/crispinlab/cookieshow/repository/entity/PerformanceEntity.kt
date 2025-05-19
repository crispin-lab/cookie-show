package com.crispinlab.cookieshow.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "performances")
internal class PerformanceEntity(
    @Column(nullable = false)
    val title: String,
    @Column(nullable = false)
    val description: String,
    @Column(
        name = "venue_id",
        nullable = false
    )
    val venue: Long,
    @Column(nullable = false)
    val startTime: Instant,
    @Column(nullable = false)
    val endTime: Instant,
    @Column(nullable = false)
    val reservationStartTime: Instant,
    @Column(nullable = false)
    val reservationEndTime: Instant
) : BaseEntity()
