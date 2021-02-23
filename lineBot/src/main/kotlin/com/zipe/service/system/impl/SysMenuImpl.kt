package com.zipe.service.system.impl

import com.zipe.entity.SysMenu
import com.zipe.model.SysMenuDto
import com.zipe.repository.ISysMenuRepository
import com.zipe.service.system.ISysMenuService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SysMenuImpl : ISysMenuService {

    @Autowired
    private lateinit var sysMenuRepository: ISysMenuRepository

    override fun getSysMenuTree(): List<SysMenuDto> {
        val sysMenus = sysMenuRepository.findByEnabledOrderByParentIdAscSequenceAsc(enabled = true)

        // Find root menu and getting the children
        return sysMenus.filter { it.parentId == 0L }
            .map { getSubMenuByRootMenuId(SysMenuDto().apply { BeanUtils.copyProperties(it, this) }, sysMenus) }
    }

    private fun getSubMenuByRootMenuId(parentMenu: SysMenuDto, sysMenus: List<SysMenu>): SysMenuDto {
        sysMenus.filter { it.parentId == parentMenu.menuId }.forEach { sysMenuVO ->
            val childVO = SysMenuDto().apply { BeanUtils.copyProperties(sysMenuVO, this) }
            getSubMenuByRootMenuId(childVO, sysMenus)
            parentMenu.children.add(childVO)
        }
        return parentMenu
    }
}
