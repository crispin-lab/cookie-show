package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.VenueEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VenueRepository : JpaRepository<VenueEntity, Long>
