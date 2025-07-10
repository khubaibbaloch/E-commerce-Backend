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

class OrderController(
    private val orderUseCase: OrderUseCase
) {

    suspend fun ping(call: ApplicationCall) {
        call.respond(HttpStatusCode.OK, mapOf("message" to "OrderRoute is working"))
    }

    suspend fun placeOrder(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

        val request = call.receive<OrderRequest>()
        val orderEntity = request.toEntity(userId)
        val orderId = orderUseCase.placeOrder(orderEntity)
        call.respond(HttpStatusCode.Created, mapOf("orderId" to orderId))
    }

    suspend fun getOrders(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Unauthorized")

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
