package com.eprogramar.blog

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration

@Configuration
class DataLoader(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository,
) : CommandLineRunner {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        run {
            this.loadUser()
            this.loadCategories()
        }
    }

    private fun loadUser() {
        userRepository.count().takeIf { it == 0L }?.let {
            userRepository.save(User(name = "Administrator", email = "admin@blog.com", password = "admin"))
                .also { logger.info(it.toString()) }
        }
    }

    private fun loadCategories() {
        categoryRepository.count().takeIf { it == 0L }?.let {
            categoryRepository.saveAll(this.getCategories())
        }
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
}
