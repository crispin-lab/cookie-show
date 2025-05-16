package com.crispinlab.cookieshow.application.service.extensions

import com.crispinlab.cookieshow.application.domain.Performance
import com.crispinlab.cookieshow.repository.entity.PerformanceEntity

internal fun Performance.toEntity(): PerformanceEntity =
    PerformanceEntity(
        title = this.title,
        description = this.description,
        venue = this.venue
    )
