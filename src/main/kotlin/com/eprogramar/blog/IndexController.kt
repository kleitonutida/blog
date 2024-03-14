package com.eprogramar.blog

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/")
class IndexController(
    private val categoryRepository: CategoryRepository,
    private val articleRepository: ArticleRepository,
) {
    private val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping
    fun index(
        @RequestParam(name = "page", required = false, defaultValue = "0") page: Int,
        model: Model,
    ): String {
        logger.info("[index] Open index")
        val articles = articleRepository.findAll(PageRequest.of(page, 1))
        val nextPage = if (page >= articles.totalElements - 1) page else page + 1
        val previousPage = if (page <= 0) 0 else page - 1

        model.addAttribute("categories", categoryRepository.findAll())
        model.addAttribute("articles", articles)
        model.addAttribute("nextPage", nextPage)
        model.addAttribute("previousPage", previousPage)
        model.addAttribute("isFirst", articles.isFirst)
        model.addAttribute("isLast", articles.isLast)

        return "index"
    }
}
