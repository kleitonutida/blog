package com.eprogramar.blog

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToOne

@Entity
data class Author(
    @Id
    @GeneratedValue
    var id: Long = 0,
    val about: String = "",

    @OneToOne
    val user: User = User(),

    var facebook: String = "",
    var twitter: String = "",
    var linkedIn: String = "",
)
