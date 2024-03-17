package com.eprogramar.blog.controller

import com.eprogramar.blog.model.User
import com.eprogramar.blog.repository.UserRepository
import jakarta.servlet.http.HttpSession
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/login")
class LoginController(private val repository: UserRepository) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun form(model: Model): String {
        logger.info("[form] Open form")
        model.addAttribute("user", User())
        return "login"
    }

    @PostMapping
    fun login(
        user: User,
        model: Model,
        session: HttpSession,
    ): String {
        logger.info("[login] Login user: $user")

        repository.findByEmail(user.email).orElse(null).let {
            when {
                it == null -> {
                    val messageError = "Usuário não encontrado!"
                    logger.error(messageError)
                    model.addAttribute("messageError", messageError)
                    return "login"
                }

                it.password != user.password -> {
                    val messageError = "Senha inválida!"
                    logger.error(messageError)
                    model.addAttribute("messageError", messageError)
                    return "login"
                }

                else -> {
                    logger.info("Login executado com sucesso!!!")
                    session.setAttribute("currentUser", it)
                    return "redirect:/"
                }
            }
        }
    }

    @GetMapping("/logout")
    fun logout(session: HttpSession): String {
        logger.info("[logout] Logout user")
        session.invalidate()
        return "redirect:/login"
    }
}
