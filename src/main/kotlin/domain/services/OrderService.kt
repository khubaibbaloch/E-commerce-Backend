package com.commerce.domain.services

import com.commerce.data.db.repository.OrderRepository
import domain.models.order.OrderEntity

class OrderService(private val orderRepository: OrderRepository) {
    suspend fun placeOrder(order: OrderEntity): String{
        return orderRepository.placeOrder(order)
    }

    suspend fun getOrdersByUser(userId: String): List<OrderEntity>{
        return orderRepository.getOrdersByUser(userId)
    }

    suspend fun cancelOrder(orderId: String, userId: String): Boolean {
        val order = orderRepository.getOrderById(orderId)
        return if (order != null && order.userId == userId && order.status == "PENDING") {
            orderRepository.updateOrderStatus(orderId, "CANCELLED")
            true
        } else {
            false
        }
    }

}