package com.zipe.controller

import com.zipe.base.controller.BaseController
import com.zipe.service.ISysMenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = ["/system"])
class SystemController : BaseController() {

    @Autowired
    lateinit var sysMenuService: ISysMenuService

    @GetMapping("/menuTree")
    fun sysMenuTree() = sysMenuService.getSysMenuTree()
}
