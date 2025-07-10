package com.commerce.presentation.user.routes

import com.commerce.domain.user.order.usecase.OrderUseCase
import com.commerce.presentation.user.controllers.OrderController
import io.ktor.server.routing.*

/**
 * Defines the HTTP routes for user order-related operations.
 * These include placing an order, viewing user orders, and cancelling orders.
 *
 * Base path: /orders
 */
fun Route.orderRoutes(orderUseCase: OrderUseCase) {
    // Initialize the OrderController with the required use case
    val controller = OrderController(orderUseCase)

    // Define all routes under the "/orders" path
    route("/orders") {

        // Health check route to verify that order routing is functional
        get("/ping") { controller.ping(call) }

        // Place a new order for the authenticated user
        post { controller.placeOrder(call) }

        // Get all orders placed by the authenticated user
        get { controller.getOrders(call) }

        // Cancel a specific order by ID, if it's in a cancellable state
        put("/cancel/{orderId}") { controller.cancelOrder(call) }
    }
}
