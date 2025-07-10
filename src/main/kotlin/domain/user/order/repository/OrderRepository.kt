package com.commerce.domain.user.order.repository

import com.commerce.domain.user.order.model.OrderEntity

/**
 * Repository interface responsible for managing order data in the persistence layer.
 * Provides CRUD-like operations for working with user orders.
 */
interface OrderRepository {

    /**
     * Persists a new order and returns its generated unique ID.
     *
     * @param order The [OrderEntity] to be saved.
     * @return The unique identifier (UUID) of the newly created order.
     */
    suspend fun placeOrder(order: OrderEntity): String

    /**
     * Retrieves all orders placed by a specific user.
     *
     * @param userId The ID of the user whose orders are to be retrieved.
     * @return A list of [OrderEntity] objects associated with the user.
     */
    suspend fun getOrdersByUser(userId: String): List<OrderEntity>

    /**
     * Retrieves a specific order by its unique order ID.
     *
     * @param orderId The unique identifier of the order.
     * @return The [OrderEntity] if found, or null if the order does not exist.
     */
    suspend fun getOrderById(orderId: String): OrderEntity?

    /**
     * Updates the status of an existing order.
     *
     * @param orderId The unique identifier of the order to update.
     * @param newStatus The new status to set (e.g., "CANCELLED", "COMPLETED").
     */
    suspend fun updateOrderStatus(orderId: String, newStatus: String): Unit
}
