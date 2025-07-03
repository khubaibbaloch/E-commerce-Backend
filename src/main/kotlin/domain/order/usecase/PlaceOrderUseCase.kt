package com.commerce.domain.order.usecase

import com.commerce.domain.order.model.OrderEntity
import com.commerce.domain.order.service.OrderService

class PlaceOrderUseCase(private val orderService: OrderService) {
    suspend operator fun invoke(order: OrderEntity): String{
        return orderService.placeOrder(order)
    }
}

