package com.zipe.service

import com.zipe.entity.MessageSetting
import com.zipe.model.output.SysMenuOutput

interface ISysMenuService {

    fun getSysMenuTree(): List<SysMenuOutput>
}
