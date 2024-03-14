package com.eprogramar.blog

import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.time.LocalDateTime

@Controller
@RequestMapping("/article")
class ArticleController(
    private val articleRepository: ArticleRepository,
    private val authorRepository: AuthorRepository,
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun form(model: Model): String {
        logger.info("Article form")
        model.addAttribute("article", Article())
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
        articleRepository.save(article)

        val messageSuccess = "Artigo criado com sucesso!"
        logger.info(messageSuccess)

        // Adiciona um atributo na sessão para ser usado na próxima requisição, ele é removido da sessão após ser usado
        redirectAttributes.addFlashAttribute("messageSuccess", messageSuccess)

        return "redirect:/"
    }
}