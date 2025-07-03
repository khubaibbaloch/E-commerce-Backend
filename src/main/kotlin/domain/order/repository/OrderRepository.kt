package com.commerce.domain.order.repository

import com.commerce.domain.order.model.OrderEntity

interface OrderRepository {
    suspend fun placeOrder(order: OrderEntity): String
    suspend fun getOrdersByUser(userId: String): List<OrderEntity>
    suspend fun getOrderById(orderId: String): OrderEntity?
    suspend fun updateOrderStatus(orderId: String, newStatus: String): Unit
}