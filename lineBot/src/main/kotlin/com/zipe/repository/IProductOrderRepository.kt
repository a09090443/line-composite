package com.zipe.repository

import com.zipe.entity.ProductOrder
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IProductOrderRepository : CrudRepository<ProductOrder, Long> {

    fun findByOrderId(orderId: String): ProductOrder

    fun findByLineIdAndStatus(lineId: String, status: String): List<ProductOrder>

    fun findByTransactionId(transactionId: Long): ProductOrder
}
