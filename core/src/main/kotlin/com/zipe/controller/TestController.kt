package com.zipe.controller

import com.zipe.util.log.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/core")
class TestController {
    val logger = logger()

    @GetMapping(value = ["/test"])
    fun test(): String {
        val test = "TEST"
        return test
    }
}
