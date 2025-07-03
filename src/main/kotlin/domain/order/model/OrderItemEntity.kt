package com.commerce.domain.order.model

data class OrderItemEntity(
    val productId: String,
    val quantity: Int,
    val price: Double
)
