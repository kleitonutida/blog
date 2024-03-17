package com.eprogramar.blog.controller

import com.eprogramar.blog.model.Article
import com.eprogramar.blog.model.User
import com.eprogramar.blog.service.ArticleService
import com.eprogramar.blog.service.CategoryService
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/article")
class ArticleController(
    private val articleService: ArticleService,
    private val categoryService: CategoryService,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun form(model: Model): String {
        logger.info("Article form")
        model.addAttribute("article", Article())
        model.addAttribute("categories", categoryService.findAll())
        return "article"
    }

    @PostMapping
    fun save(
        article: Article,
        session: HttpSession,
        redirectAttributes: RedirectAttributes,
    ): String {
        logger.info("Saving article: $article")

        val currentUser = session.getAttribute("currentUser") as User

        articleService.save(article, currentUser)

        val messageSuccess = "Artigo criado com sucesso!"
        // Adiciona um atributo na sessão para ser usado na próxima requisição, ele é removido da sessão após ser usado
        redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess)

        logger.info(messageSuccess)
        return "redirect:/"
    }

    @GetMapping("/list")
    fun list(model: Model): String {
        logger.info("List articles")
        model.addAttribute("articles", articleService.findAll())
        model.addAttribute("categories", categoryService.findAll())
        return "article-list"
    }

    @GetMapping("/list/user/{id}")
    fun listByAuthor(
        @PathVariable id: Long,
        model: Model,
    ): String {
        logger.info("List articles by user id: $id")
        val sort = Sort.by(Sort.Direction.DESC, "id")
        model.addAttribute("articles", articleService.findByAuthorUserId(id, sort))
        model.addAttribute("categories", categoryService.findAll())
        return "article-list"
    }

    @GetMapping("/list/category/{id}")
    fun listByCategory(
        @PathVariable id: Long,
        model: Model,
    ): String {
        logger.info("List articles by category id: $id")
        val sort = Sort.by(Sort.Direction.DESC, "id")
        model.addAttribute("articles", articleService.findByCategoryId(id, sort))
        model.addAttribute("categories", categoryService.findAll())
        return "article-list"
    }

    @GetMapping("/edit/{id}")
    fun edit(
        @PathVariable id: Long,
        model: Model,
    ): String {
        logger.info("Edit article id: $id")
        model.addAttribute("article", articleService.findById(id))
        model.addAttribute("categories", categoryService.findAll())
        return "article"
    }

    @GetMapping("/delete/{id}")
    fun delete(
        @PathVariable id: Long,
        model: Model,
    ): String {
        logger.info("Delete article id: $id")
        articleService.deleteById(id)
        return "redirect:/article/list"
    }
}
