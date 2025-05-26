package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.PerformanceSeatEntity
import org.springframework.data.jpa.repository.JpaRepository

internal interface PerformanceSeatRepository : JpaRepository<PerformanceSeatEntity, Long>
