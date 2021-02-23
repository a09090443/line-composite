package com.zipe.model

data class SysMenuDto(

    var menuId: Long = 0,

    var menuName: String = "",

    var path: String = "",

    var comment: String = "",

    var enabled: Boolean = true,

    var seq: String = "",

    var parentId: Long = 0,

    var children: MutableList<SysMenuDto> = mutableListOf()

)
