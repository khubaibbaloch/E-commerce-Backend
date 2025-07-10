package com.commerce.presentation.user.routes

import com.commerce.domain.user.order.usecase.OrderUseCase
import com.commerce.presentation.user.controllers.OrderController
import io.ktor.server.routing.*

//fun Route.orderRoutes(orderService: OrderService) {
//    route("/orders") {
//
//        get("/ping") {
//            call.respond(HttpStatusCode.OK, mapOf("message" to "OrderRoute is working"))
//        }
//
//        post {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//                ?: return@post call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
//
//            val request = call.receive<OrderRequest>()
//            val orderEntity = request.toEntity(userId)
//            val orderId = orderService.placeOrder(orderEntity)
//            call.respond(HttpStatusCode.Created, mapOf("orderId" to orderId))
//        }
//
//        get {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//                ?: return@get call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
//
//            val orders = orderService.getOrdersByUser(userId)
//            val response = orders.map {
//                OrderResponse(
//                    orderId = it.orderId,
//                    totalPrice = it.totalPrice,
//                    status = it.status,
//                    createdAt = it.createdAt,
//                    items = it.items.map { item ->
//                        OrderItemResponse(
//                            productId = item.productId,
//                            quantity = item.quantity,
//                            price = item.price
//                        )
//                    }
//                )
//            }
//            call.respond(HttpStatusCode.OK, response)
//        }
//
//        put("/cancel/{orderId}") {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//                ?: return@put call.respond(HttpStatusCode.Unauthorized, "Unauthorized")
//
//            val orderId = call.parameters["orderId"] ?: return@put call.respond(
//                HttpStatusCode.BadRequest, "Order ID is required"
//            )
//
//            val success = orderService.cancelOrder(orderId, userId)
//            if (success) {
//                call.respond(HttpStatusCode.OK, "Order cancelled")
//            } else {
//                call.respond(HttpStatusCode.BadRequest, "Cannot cancel this order")
//            }
//        }
//
//    }
//}


fun Route.orderRoutes(orderUseCase: OrderUseCase) {
    val controller = OrderController(orderUseCase)

    route("/orders") {
        get("/ping") { controller.ping(call) }

        post { controller.placeOrder(call) }
        get { controller.getOrders(call) }
        put("/cancel/{orderId}") { controller.cancelOrder(call) }

    }
}