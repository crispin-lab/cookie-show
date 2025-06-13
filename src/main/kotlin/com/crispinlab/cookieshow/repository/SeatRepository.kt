package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.SeatEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

internal interface SeatRepository : JpaRepository<SeatEntity, Long> {
    fun findAllByVenue(venueId: Long): List<SeatEntity>

    @Modifying
    @Query(
        value = """
        UPDATE SeatEntity s
        SET s.isAvailable = :isAvailable
        WHERE s.id = :seatId
    """
    )
    fun updateAvailability(
        @Param("seatId") seatId: Long,
        @Param("isAvailable") isAvailable: Boolean
    ): Int
}
