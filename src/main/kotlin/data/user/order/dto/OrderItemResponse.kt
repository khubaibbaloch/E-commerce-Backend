package com.commerce.data.user.order.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing a single item in the response for an order.
 * Used to return order details back to the client (e.g., in order history or confirmation).
 */
@Serializable
data class OrderItemResponse(

    // The ID of the product included in the order
    val productId: String,

    // Quantity of this product that was ordered
    val quantity: Int,

    // Final price of the product at the time of the order (used for display and invoice)
    val price: Double
)
