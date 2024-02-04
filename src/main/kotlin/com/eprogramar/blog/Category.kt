package com.eprogramar.blog

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Category(
    @Id @GeneratedValue var id: Long = 0,
    var name: String,
)
