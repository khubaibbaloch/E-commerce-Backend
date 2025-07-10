package com.commerce.domain.user.order.model

/**
 * Domain model representing an individual item in a user's order.
 *
 * Each order can contain multiple order items, and each item contains details
 * about the product, its quantity, and the price at the time of ordering.
 *
 * @property productId The unique identifier of the product being ordered.
 * @property quantity The number of units of the product ordered.
 * @property price The price of a single unit of the product at the time of purchase.
 */
data class OrderItemEntity(
    val productId: String,
    val quantity: Int,
    val price: Double
)
