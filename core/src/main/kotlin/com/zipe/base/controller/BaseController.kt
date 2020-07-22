package com.zipe.base.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


abstract class BaseController {
    protected lateinit var request: HttpServletRequest
    protected lateinit var response: HttpServletResponse

    private lateinit var currentLocale: Locale

    @Autowired
    protected lateinit var messageSource: MessageSource

    @ModelAttribute
    open fun myModel(request: HttpServletRequest, response: HttpServletResponse, model: Model) {
        this.request = request
        this.response = response
        currentLocale = LocaleContextHolder.getLocale()
    }

    /**
     * This method returns the principal[user-name] of logged-in user.
     */
    protected open fun getPrincipal(): String {
        val principal = SecurityContextHolder.getContext().authentication.principal
        return if (principal is UserDetails) {
            principal.username
        } else {
            principal.toString()
        }
    }

    protected open fun getMessage(key: String, vararg args: String): String {
        return if (key.isBlank()) {
            ""
        } else {
            messageSource.getMessage(key, args, currentLocale)
        }
    }
}
