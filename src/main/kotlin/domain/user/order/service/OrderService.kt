package com.commerce.domain.user.order.service

import com.commerce.domain.user.order.model.OrderEntity

/**
 * Service interface that contains business logic operations for handling user orders.
 * It abstracts the operations that can be performed on orders from the rest of the application.
 */
interface OrderService {

    /**
     * Places a new order for the user.
     *
     * @param order The [OrderEntity] containing order details including items and pricing.
     * @return The generated unique ID of the placed order.
     */
    suspend fun placeOrder(order: OrderEntity): String

    /**
     * Retrieves all orders that have been placed by a specific user.
     *
     * @param userId The ID of the user whose order history is being retrieved.
     * @return A list of [OrderEntity] objects for the specified user.
     */
    suspend fun getOrdersByUser(userId: String): List<OrderEntity>

    /**
     * Cancels an existing order if it belongs to the user and is still pending.
     *
     * @param orderId The unique ID of the order to be canceled.
     * @param userId The ID of the user requesting the cancellation.
     * @return `true` if the order was successfully canceled, `false` otherwise.
     */
    suspend fun cancelOrder(orderId: String, userId: String): Boolean
}
