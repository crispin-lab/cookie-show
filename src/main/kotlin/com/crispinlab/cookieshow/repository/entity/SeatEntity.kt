package com.crispinlab.cookieshow.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "seats")
internal class SeatEntity(
    @Column(
        name = "venue_id",
        nullable = false
    )
    val venue: Long,
    @Column(
        name = "\"row\"",
        nullable = false
    )
    val row: String,
    @Column(nullable = false)
    val number: Int,
    @Column(nullable = false)
    val isAvailable: Boolean = true
) : BaseEntity()
