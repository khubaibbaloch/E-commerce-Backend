package com.commerce.presentation.user.controllers

import com.commerce.data.user.order.dto.OrderItemResponse
import com.commerce.data.user.order.dto.OrderRequest
import com.commerce.data.user.order.dto.OrderResponse
import com.commerce.data.user.order.mapper.toEntity
import com.commerce.domain.user.order.usecase.OrderUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*

/**
 * Controller responsible for handling user order operations.
 * Actions include placing orders, retrieving user orders, and cancelling orders.
 */
class OrderController(
    private val orderUseCase: OrderUseCase
) {

    /**
     * Simple health check for the order route.
     * Use this to test if the route is active.
     */
    suspend fun ping(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, mapOf("message" to "OrderRoute is working"))
    }

    /**
     * Places a new order for the authenticated user.
     * Requires: JWT with userId
     * Body: OrderRequest containing item list (productId, quantity, price)
     * Returns: orderId in response
     */
    suspend fun placeOrder(call: ApplicationCall) {
        // Extract userId from JWT
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

        // Receive request body and map to domain entity
        val request = call.receive<OrderRequest>()
        val orderEntity = request.toEntity(userId)

        // Place order and return orderId
        val orderId = orderUseCase.placeOrder(orderEntity)
        call.respond(HttpStatusCode.Created, mapOf("orderId" to orderId))
    }

    /**
     * Retrieves all orders for the authenticated user.
     * Requires: JWT with userId
     * Returns: List of OrderResponse (orderId, total, status, date, items)
     */
    suspend fun getOrders(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

        // Get orders and map them to DTO response format
        val orders = orderUseCase.getOrdersByUser(userId)
        val response = orders.map {
            OrderResponse(
                orderId = it.orderId,
                totalPrice = it.totalPrice,
                status = it.status,
                createdAt = it.createdAt,
                items = it.items.map { item ->
                    OrderItemResponse(
                        productId = item.productId,
                        quantity = item.quantity,
                        price = item.price
                    )
                }
            )
        }

        call.respond(HttpStatusCode.OK, response)
    }

    /**
     * Cancels an order for the authenticated user.
     * Only "PENDING" orders are cancellable.
     * Requires: JWT with userId
     * Path param: orderId
     * Returns: success or error message
     */
    suspend fun cancelOrder(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

        val orderId = call.parameters["orderId"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Order ID is required")

        val success = orderUseCase.cancelOrder(orderId, userId)
        if (success) {
            call.respond(HttpStatusCode.OK, "Order cancelled")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Cannot cancel this order")
        }
    }
}
