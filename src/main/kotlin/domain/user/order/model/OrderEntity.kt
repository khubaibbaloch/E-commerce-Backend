package com.commerce.domain.user.order.model

/**
 * Domain model representing a complete order placed by a user.
 *
 * This class is used to carry order-related information across the system.
 *
 * @property orderId Unique identifier for the order.
 * @property userId Identifier of the user who placed the order.
 * @property totalPrice Total amount charged for the entire order.
 * @property status Current status of the order (e.g., "PENDING", "COMPLETED", "CANCELLED").
 * @property createdAt Timestamp (in milliseconds) representing when the order was created.
 * @property items List of items included in the order, each represented by [OrderItemEntity].
 */
data class OrderEntity(
    val orderId: String,
    val userId: String,
    val totalPrice: Double,
    val status: String,
    val createdAt: Long,
    val items: List<OrderItemEntity>
)
