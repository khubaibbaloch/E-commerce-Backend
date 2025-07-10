package com.commerce.domain.user.order.usecase

import com.commerce.domain.user.order.model.OrderEntity
import com.commerce.domain.user.order.service.OrderService

/**
 * Use case responsible for placing a new order.
 *
 * @property orderService Service that handles order-related operations.
 */
class PlaceOrderUseCase(private val orderService: OrderService) {

    /**
     * Executes the use case to place an order.
     *
     * @param order The order entity containing order details.
     * @return The generated order ID as a [String].
     */
    suspend operator fun invoke(order: OrderEntity): String {
        return orderService.placeOrder(order)
    }
}
