package com.zipe.repository

import com.zipe.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IProductRepository : JpaRepository<Product, Long> {

    fun findByName(name: String): Product

    fun findByProductIdAndChannelId(productId: String, channelId: String): Product?

}
