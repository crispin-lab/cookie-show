package com.crispinlab.cookieshow.repository.entity

import jakarta.persistence.CollectionTable
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table

@Entity
@Table(name = "venues")
internal class VenueEntity(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val address: String,
    @Column(nullable = false)
    val capacity: Int,
    @ElementCollection
    @CollectionTable(
        name = "venue_row_ids",
        joinColumns = [JoinColumn(name = "venue_id")]
    )
    @Column(name = "row_id")
    val rows: List<Long> = emptyList()
) : BaseEntity()
