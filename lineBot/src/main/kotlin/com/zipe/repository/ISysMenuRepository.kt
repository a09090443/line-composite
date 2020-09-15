package com.zipe.repository

import com.zipe.entity.SysMenu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ISysMenuRepository : JpaRepository<SysMenu, Long> {
    @Query("SELECT menu FROM SysMenu menu WHERE menu.enabled = true ORDER BY menu.parentId ASC, menu.seq ASC")
    fun findEnabled(): List<SysMenu>
//    fun findByMenuId(menuId: Long, pageable: Pageable): List<SysMenu>
}
