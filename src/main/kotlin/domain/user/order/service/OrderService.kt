package com.commerce.domain.order.service

import com.commerce.domain.order.model.OrderEntity

interface OrderService {
    suspend fun placeOrder(order: OrderEntity): String
    suspend fun getOrdersByUser(userId: String): List<OrderEntity>
    suspend fun cancelOrder(orderId: String, userId: String): Boolean
}