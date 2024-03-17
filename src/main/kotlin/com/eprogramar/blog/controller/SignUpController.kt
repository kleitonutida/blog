package com.eprogramar.blog.controller

import com.eprogramar.blog.model.User
import com.eprogramar.blog.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/signup")
class SignUpController(private val service: UserService) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @GetMapping
    fun form(model: Model): String {
        logger.info("[form] Open form")
        model.addAttribute("user", User())
        return "signup"
    }

    @PostMapping
    fun save(
        user: User,
        confirmPassword: String,
        model: Model,
    ): String {
        logger.info("[save] Save user $user")

        if (user.password != confirmPassword) {
            val passwordError = "Senha não confere"
            logger.info(passwordError)
            model.addAttribute("passwordError", passwordError)
            return "signup"
        }

        service.save(user).also { logger.info(it.toString()) }

        return "redirect:/login"
    }
}
