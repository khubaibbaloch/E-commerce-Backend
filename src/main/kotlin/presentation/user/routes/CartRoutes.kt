package com.commerce.presentation.user.routes

import com.commerce.domain.user.cart.usecase.CartUseCase
import io.ktor.server.routing.*
import com.commerce.presentation.user.controllers.CartController


//fun Route.cartRoutes(cartService: CartService) {
//    route("/carts") {
//        // Health or basic test route
//        get("/ping") {
//            call.respond(mapOf("message" to "Cart route is working"))
//        }
//        post {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//
//            if (userId == null) {
//                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")
//                return@post
//            }
//
//            val cartRequest = call.receive<CartRequest>()
//            val cartEntity = cartRequest.toDomain(userId)
//            val result = cartService.addCart(cartEntity)
//            call.respond(HttpStatusCode.Created, mapOf("cartId" to result))
//
//        }
//        get {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//
//            if (userId == null) {
//                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")
//                return@get
//            }
//
//            val cartList = cartService.findCartByUserId(userId)
//            val responseList = cartList.map { it.toResponse() }
//            call.respond(HttpStatusCode.OK, responseList)
//
//        }
//
//        put("/{cartId}") {
//            val cartId = call.parameters["cartId"] ?: return@put call.respond(
//                HttpStatusCode.BadRequest,
//                "Cart ID is required"
//            )
//            val updatedCart = call.receive<CartUpdateRequest>()
//            val updateModel = updatedCart.toDomain()
//            val wasUpdated = cartService.updateCart(cartId, updateModel)
//            if (wasUpdated) {
//                call.respond(HttpStatusCode.OK, "Cart updated successfully")
//            } else {
//                call.respond(HttpStatusCode.InternalServerError, "Failed to update cart")
//            }
//        }
//
//        delete("/{cartId}") {
//            val cartId = call.parameters["cartId"] ?: return@delete call.respond(
//                HttpStatusCode.BadRequest,
//                "Cart ID is required"
//            )
//            val wasDeleted = cartService.deleteCart(cartId)
//
//            if (wasDeleted) {
//                call.respond(HttpStatusCode.OK, "Cart deleted successfully")
//            } else {
//                call.respond(HttpStatusCode.InternalServerError, "Failed to delete cart")
//            }
//        }
//    }
//}


fun Route.cartRoutes(cartUseCase: CartUseCase) {
    val controller = CartController(cartUseCase)

    route("/carts") {
        get("/ping") { controller.ping(call) }

        post { controller.addCart(call) }
        get { controller.getCart(call) }
        put("/{cartId}") { controller.updateCart(call) }
        delete("/{cartId}") { controller.deleteCart(call) }

    }
}
