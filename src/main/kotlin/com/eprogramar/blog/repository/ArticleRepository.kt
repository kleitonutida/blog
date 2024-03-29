package com.eprogramar.blog.repository

import com.eprogramar.blog.model.Article
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : JpaRepository<Article, Long> {
    fun findByAuthorUserId(
        id: Long,
        sort: Sort,
    ): List<Article>

    fun findByCategoryId(
        id: Long,
        sort: Sort,
    ): List<Article>
}
