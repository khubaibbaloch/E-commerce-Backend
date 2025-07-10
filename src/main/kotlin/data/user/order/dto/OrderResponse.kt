package com.commerce.data.user.order.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) representing the response returned
 * after an order is successfully processed or fetched.
 *
 * @property orderId Unique identifier of the order.
 * @property totalPrice Total cost of all items in the order.
 * @property status Current status of the order (e.g., "Pending", "Completed").
 * @property createdAt Timestamp (epoch millis) when the order was created.
 * @property items List of individual items included in the order.
 */
@Serializable
data class OrderResponse(
    val orderId: String,
    val totalPrice: Double,
    val status: String,
    val createdAt: Long,
    val items: List<OrderItemResponse>
)
