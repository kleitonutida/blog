package com.eprogramar.blog.service

import com.eprogramar.blog.model.Article
import com.eprogramar.blog.model.Author
import com.eprogramar.blog.model.User
import com.eprogramar.blog.repository.ArticleRepository
import com.eprogramar.blog.repository.AuthorRepository
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ArticleService(
    private val articleRepository: ArticleRepository,
    private val authorRepository: AuthorRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun save(
        article: Article,
        currentUser: User,
    ): Article {
        logger.info("Saving article: $article")

        val author =
            authorRepository.findByUserId(currentUser.id).let { author ->
                if (author.isPresent) {
                    author.get()
                } else {
                    val newAuthor = Author(user = currentUser)
                    authorRepository.save(newAuthor).also { logger.info("Autor criado com sucesso!") }
                }
            }

        article.author = author
        article.date = LocalDateTime.now()
        return articleRepository.save(article)
    }

    fun findAll(page: Int = 0): Page<Article> {
        val pageRequest = PageRequest.of(page, 1, Sort.by(Sort.Direction.DESC, "id"))
        return articleRepository.findAll(pageRequest)
    }

    fun findByAuthorUserId(
        id: Long,
        sort: Sort,
    ): List<Article> {
        return articleRepository.findByAuthorUserId(id, sort)
    }

    fun findByCategoryId(
        id: Long,
        sort: Sort,
    ): List<Article> {
        return articleRepository.findByCategoryId(id, sort)
    }

    fun findById(id: Long): Article {
        return articleRepository.findById(id).get()
    }

    fun deleteById(id: Long) {
        articleRepository.deleteById(id)
    }
}
