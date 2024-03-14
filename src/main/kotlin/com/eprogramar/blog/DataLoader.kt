package com.eprogramar.blog

import net.datafaker.Faker
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime

@Configuration
class DataLoader(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
    private val articleRepository: ArticleRepository,
    private val authorRepository: AuthorRepository,
) : CommandLineRunner {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        run {
            this.loadUser()
            this.loadCategories()
            this.loadArticles()
        }
    }

    private fun loadUser() =
        userRepository.count().takeIf { it == 0L }?.let {
            userRepository.save(User(name = "Administrator", email = "admin@blog.com", password = "admin"))
                .also { logger.info(it.toString()) }
        }

    private fun loadCategories() =
        categoryRepository.count().takeIf { it == 0L }?.let {
            categoryRepository.saveAll(this.getCategories())
        }

    private fun getCategories() =
        listOf(
            Category(name = "World"),
            Category(name = "U.S."),
            Category(name = "Technology"),
            Category(name = "Design"),
            Category(name = "Culture"),
            Category(name = "Business"),
            Category(name = "Politics"),
            Category(name = "Opinion"),
            Category(name = "Science"),
            Category(name = "Health"),
            Category(name = "Style"),
            Category(name = "Travel"),
        )

    private fun loadArticles() =
        articleRepository.count().takeIf { it == 0L }?.let {
            val user = userRepository.findAll()[0]
            val author = authorRepository.save(Author(user = user))

            val articles = mutableListOf<Article>()
            val faker = Faker()

            for (i in 1..10) {
                articles.add(
                    Article(
                        title = faker.lorem().sentence(2),
                        subTitle = faker.lorem().sentence(8),
                        content = faker.lorem().sentence(20),
                        date = LocalDateTime.now(),
                        author = author,
                    ),
                )
            }

            articles.also { articleRepository.saveAll(it) }
        }
}
