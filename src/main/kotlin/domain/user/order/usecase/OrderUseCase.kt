package com.commerce.domain.user.order.usecase

import domain.user.order.usecase.CancelOrderUseCase
import domain.user.order.usecase.GetOrdersByUserUseCase

/**
 * Aggregates all order-related use cases into a single container.
 * This helps organize and inject order use cases easily throughout the app.
 *
 * @property placeOrder Use case to place a new order.
 * @property getOrdersByUser Use case to retrieve orders for a specific user.
 * @property cancelOrder Use case to cancel an existing order.
 */
data class OrderUseCase(
    val placeOrder: PlaceOrderUseCase,
    val getOrdersByUser: GetOrdersByUserUseCase,
    val cancelOrder: CancelOrderUseCase
)
