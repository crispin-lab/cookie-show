package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.PerformanceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
internal interface PerformanceRepository : JpaRepository<PerformanceEntity, Long> {
    @Query(
        value =
            """
            SELECT COUNT(*)
            FROM (
                SELECT performances.id
                FROM performances
                LIMIT :limit
            ) t
            """,
        nativeQuery = true
    )
    fun count(
        @Param("limit") limit: Long
    ): Long

    @Query(
        value =
            """
            SELECT performances.id, performances.title, performances.description, performances.venue_id, performances.start_time, performances.end_time,
                performances.reservation_start_time, performances.reservation_end_time
            FROM (
                SELECT performances.id
                FROM performances
                LIMIT :limit OFFSET :offset
            ) t
            LEFT JOIN performances ON t.id = performances.id
            """,
        nativeQuery = true
    )
    fun findAll(
        @Param("offset") offset: Long,
        @Param("limit") limit: Long
    ): List<PerformanceEntity>
}
