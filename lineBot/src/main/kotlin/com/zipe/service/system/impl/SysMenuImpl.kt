package com.zipe.service.system.impl

import com.zipe.entity.SysMenu
import com.zipe.model.output.SysMenuOutput
import com.zipe.repository.ISysMenuRepository
import com.zipe.service.system.ISysMenuService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SysMenuImpl : ISysMenuService {

    @Autowired
    private lateinit var sysMenuRepository: ISysMenuRepository

    override fun getSysMenuTree(): List<SysMenuOutput> {
        val sysMenus = sysMenuRepository.findByEnabledOrderByParentIdAscSequenceAsc(enabled = true)

        // Find root menu and getting the children
        return sysMenus.filter { it.parentId == 0L }
            .map { getSubMenuByRootMenuId(SysMenuOutput().apply { BeanUtils.copyProperties(it, this) }, sysMenus) }
    }

    private fun getSubMenuByRootMenuId(parentMenu: SysMenuOutput, sysMenus: List<SysMenu>): SysMenuOutput {
        sysMenus.filter { it.parentId == parentMenu.menuId }.forEach { sysMenuVO ->
            val childVO = SysMenuOutput().apply { BeanUtils.copyProperties(sysMenuVO, this) }
            getSubMenuByRootMenuId(childVO, sysMenus)
            parentMenu.children.add(childVO)
        }
        return parentMenu
    }
}
