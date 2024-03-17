package com.eprogramar.blog

import net.datafaker.Faker
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
    private val faker = Faker()

    override fun run(vararg args: String?) {
        run {
            this.loadUser()
            this.loadCategories()
            this.loadArticles()
        }
    }

    private fun loadUser() =
        userRepository.count().takeIf { it == 0L }?.let {
            val users = mutableListOf<User>()
            users.add(
                User(name = "Administrator", email = "admin@blog.com", password = "admin"),
            )
            users.add(
                User(
                    name = faker.name().firstName(),
                    email = faker.internet().emailAddress(),
                    password = faker.internet().password(),
                ),
            )

            userRepository.saveAll(users)
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
            val user = userRepository.findAll()
            val categories = categoryRepository.findAll()

            val author =
                authorRepository.saveAll(
                    listOf(
                        Author(
                            user = user[0],
                            about = faker.lorem().sentence(),
                            facebook = "https://facebook.com/administrator",
                            twitter = "https://twitter.com/administrator",
                            linkedIn = "https://linkedin.com/administrator",
                        ),
                        Author(
                            user = user[1],
                            about = faker.lorem().sentence(),
                            facebook = "https://facebook.com/${user[1].name.lowercase()}",
                            twitter = "https://twitter.com/${user[1].name.lowercase()}",
                            linkedIn = "https://linkedin.com/${user[1].name.lowercase()}",
                        ),
                    ),
                )

            val articles = mutableListOf<Article>()

            for (i in 1..10) {
                articles.add(
                    Article(
                        title = faker.lorem().sentence(2),
                        subTitle = faker.lorem().sentence(8),
                        content = faker.lorem().sentence(20),
                        date = LocalDateTime.now(),
                        author = if (i % 2 == 0) author[0] else author[1],
                        category = categories[(0..<categories.size).random()],
                    ),
                )
            }

            articleRepository.saveAll(articles)
        }
}
