package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.ReservationEntity
import org.springframework.data.jpa.repository.JpaRepository

internal interface ReservationRepository : JpaRepository<ReservationEntity, Long>
