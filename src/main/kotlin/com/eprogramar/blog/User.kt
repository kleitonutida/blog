package com.eprogramar.blog

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue var id: Long = 0,
    var name: String = "",
    var email: String = "",
    var password: String = "",
)
