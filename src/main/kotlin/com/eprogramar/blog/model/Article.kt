package com.eprogramar.blog.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.time.LocalDateTime

@Entity
data class Article(
    @Id
    @GeneratedValue
    var id: Long = 0,
    var title: String = "",
    var subTitle: String = "",
    var content: String = "",
    var date: LocalDateTime = LocalDateTime.now(),
    @ManyToOne
    var author: Author = Author(),
    @ManyToOne
    var category: Category = Category(),
)
