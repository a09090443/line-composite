package com.zipe.repository

import com.zipe.entity.SysMenu
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ISysMenuRepository : JpaRepository<SysMenu, Long> {

}
