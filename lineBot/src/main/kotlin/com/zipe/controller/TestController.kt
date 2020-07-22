package com.zipe.controller

import com.zipe.base.controller.BaseController
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/test"])
class TestController : BaseController() {

    @GetMapping("/helloWorld")
    fun helloWorld() = "HelloWorld"
}
