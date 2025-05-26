package com.crispinlab.cookieshow.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "performance_seats")
internal class PerformanceSeatEntity(
    @Column(
        name = "performance_id",
        nullable = false
    )
    val performance: String,
    @Column(
        name = "seat_id",
        nullable = false
    )
    val seat: String
) : BaseEntity()
