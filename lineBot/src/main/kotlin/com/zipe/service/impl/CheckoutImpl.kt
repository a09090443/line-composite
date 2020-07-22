package com.zipe.service.impl

import com.zipe.repository.IOrderProcessRepository
import com.zipe.service.ICheckoutService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service("checkoutService")
class CheckoutImpl : ICheckoutService {

    @Autowired
    private lateinit var orderRepository: IOrderProcessRepository

    override fun createOrder() {
        TODO("Not yet implemented")
    }

}
