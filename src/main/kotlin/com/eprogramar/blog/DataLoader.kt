package com.eprogramar.blog

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration

@Configuration
class DataLoader(private val userRepository: UserRepository) : CommandLineRunner {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun run(vararg args: String?) {
        run {
            userRepository.count().takeIf { it == 0L }?.let {
                userRepository.save(User(name = "Administrator", email = "admin@blog.com", password = "admin"))
                    .also { logger.info(it.toString()) }
            }
        }
    }
}
