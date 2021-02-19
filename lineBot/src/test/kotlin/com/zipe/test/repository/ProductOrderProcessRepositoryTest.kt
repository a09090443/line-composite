package com.zipe.test.repository

import com.zipe.enum.ResourceEnum
import com.zipe.jdbc.BaseJDBC
import com.zipe.model.OrderProcess
import com.zipe.test.base.TestBase
import org.springframework.beans.factory.annotation.Autowired

class ProductOrderProcessRepositoryTest(val orderJDBC: BaseJDBC) : TestBase() {

    @Test
    fun `find order process tree view`() {
        val resource: ResourceEnum = ResourceEnum.SQL_ORDER.getResource("FIND_ORDER_PROCESS_TREE")
        val argMap = mapOf("name" to "美式咖啡", "channelId" to "1654463046")
        val orderProcess = orderJDBC.queryForList(resource, null, argMap, OrderProcess::class.java)
        println(orderProcess)
    }

}
