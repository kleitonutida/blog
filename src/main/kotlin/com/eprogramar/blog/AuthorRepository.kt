package com.eprogramar.blog

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthorRepository : JpaRepository<Author, Long> {
    fun findByUserId(id: Long): Optional<Author>
}
