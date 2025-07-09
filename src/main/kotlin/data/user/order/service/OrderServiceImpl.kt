package com.commerce.data.order.service

import com.commerce.data.order.repository.OrderRepositoryImpl
import com.commerce.domain.order.model.OrderEntity
import com.commerce.domain.order.repository.OrderRepository
import com.commerce.domain.order.service.OrderService

class OrderServiceImpl(private val orderRepository: OrderRepository):OrderService {
    override suspend fun placeOrder(order: OrderEntity): String{
        return orderRepository.placeOrder(order)
    }

    override suspend fun getOrdersByUser(userId: String): List<OrderEntity>{
        return orderRepository.getOrdersByUser(userId)
    }

    override suspend fun cancelOrder(orderId: String, userId: String): Boolean {
        val order = orderRepository.getOrderById(orderId)
        return if (order != null && order.userId == userId && order.status == "PENDING") {
            orderRepository.updateOrderStatus(orderId, "CANCELLED")
            true
        } else {
            false
        }
    }

}