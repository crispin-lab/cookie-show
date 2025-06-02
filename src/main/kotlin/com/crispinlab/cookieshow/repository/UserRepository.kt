package com.crispinlab.cookieshow.repository

import com.crispinlab.cookieshow.repository.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

internal interface UserRepository : JpaRepository<UserEntity, Long>
