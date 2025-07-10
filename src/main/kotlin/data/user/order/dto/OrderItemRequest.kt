package com.commerce.data.user.order.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a single item in a user's order request.
 * Used when a user places an order containing multiple items.
 */
@Serializable
data class OrderItemRequest(

    // The ID of the product being ordered
    val productId: String,

    // Quantity of the product to order
    val quantity: Int,

    // Price of the product at the time of ordering (for snapshot purposes)
    val price: Double
)
