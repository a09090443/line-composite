package com.zipe.repository

import com.zipe.entity.Product
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IProductRepository : CrudRepository<Product, Long> {

    fun findByName(name: String): Product

    @Cacheable(cacheNames = ["product"])
    fun findByProductIdAndLineId(productId: String, lineId: Long): Product

}
