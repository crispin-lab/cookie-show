package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.SeatEntity
import org.springframework.data.jpa.repository.JpaRepository

internal interface SeatRepository : JpaRepository<SeatEntity, Long>
