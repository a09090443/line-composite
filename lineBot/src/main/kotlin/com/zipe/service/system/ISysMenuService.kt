package com.zipe.service.system

import com.zipe.model.SysMenuDto

interface ISysMenuService {

    fun getSysMenuTree(): List<SysMenuDto>
}
