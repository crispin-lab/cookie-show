package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.PerformanceEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
internal interface PerformanceRepository : JpaRepository<PerformanceEntity, Long>
