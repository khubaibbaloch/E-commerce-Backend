package com.commerce.data.order.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderResponse(
    val orderId: String,
    val totalPrice: Double,
    val status: String,
    val createdAt: Long,
    val items: List<OrderItemResponse>
)