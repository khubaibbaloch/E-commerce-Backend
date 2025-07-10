package com.commerce.data.user.order.service

import com.commerce.domain.order.model.OrderEntity
import com.commerce.domain.order.repository.OrderRepository
import com.commerce.domain.order.service.OrderService

/**
 * Implementation of [OrderService] that handles business logic
 * related to orders, delegating persistence to [OrderRepository].
 *
 * @property orderRepository Repository used for data operations.
 */
class OrderServiceImpl(private val orderRepository: OrderRepository) : OrderService {

    /**
     * Places a new order by delegating to the repository.
     *
     * @param order The order entity to place.
     * @return The generated order ID.
     */
    override suspend fun placeOrder(order: OrderEntity): String {
        return orderRepository.placeOrder(order)
    }

    /**
     * Retrieves all orders associated with a given user ID.
     *
     * @param userId The user identifier.
     * @return List of orders for the user.
     */
    override suspend fun getOrdersByUser(userId: String): List<OrderEntity> {
        return orderRepository.getOrdersByUser(userId)
    }

    /**
     * Cancels an order if it belongs to the user, and is currently pending.
     *
     * @param orderId The ID of the order to cancel.
     * @param userId The user requesting cancellation.
     * @return True if the order was successfully cancelled; false otherwise.
     */
    override suspend fun cancelOrder(orderId: String, userId: String): Boolean {
        val order = orderRepository.getOrderById(orderId)
        return if (order != null && order.userId == userId && order.status == "PENDING") {
            orderRepository.updateOrderStatus(orderId, "CANCELLED")
            true
        } else {
            false
        }
    }
}
