package com.eprogramar.blog

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class IndexController(private val categoryRepository: CategoryRepository) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun index(model: Model): String {
        logger.info("[index] Open index")
        model.addAttribute(
            "categories",
            categoryRepository.findAll(),
        )
        return "index"
    }
}
