package com.crispinlab.cookieshow.repository.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "users")
internal class UserEntity(
    @Column(nullable = false)
    val name: String,
    @Column(nullable = false)
    val phone: String,
    @Column(nullable = false)
    val email: String
) : BaseEntity()
