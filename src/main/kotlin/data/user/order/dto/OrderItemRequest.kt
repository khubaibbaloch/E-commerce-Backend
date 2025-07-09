package com.commerce.data.order.dto

import kotlinx.serialization.Serializable

@Serializable
data class OrderItemRequest(
    val productId: String,
    val quantity: Int,
    val price: Double
)