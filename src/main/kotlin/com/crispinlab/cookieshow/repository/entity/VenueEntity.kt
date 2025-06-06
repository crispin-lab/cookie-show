package com.crispinlab.cookieshow.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "venues")
internal class VenueEntity(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val address: String,
    @Column(nullable = false)
    val capacity: Int
) : BaseEntity()
