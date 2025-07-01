package com.commerce.presentation.routes

import com.commerce.data.dto.cart.CartRequest
import com.commerce.data.dto.cart.CartUpdateRequest
import com.commerce.data.dto.cart.toDomain
import com.commerce.domain.services.CartService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.commerce.data.dto.cart.toResponse


fun Route.cartRoutes(cartService: CartService) {
    route("/carts") {
        // Health or basic test route
        get("/ping") {
            call.respond(mapOf("message" to "Cart route is working"))
        }
        post {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")
                return@post
            }

            val cartRequest = call.receive<CartRequest>()
            val cartEntity = cartRequest.toDomain(userId)
            val result = cartService.addCart(cartEntity)
            call.respond(HttpStatusCode.Created, mapOf("cartId" to result))

        }
        get {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")
                return@get
            }

            val cartList = cartService.findCartByUserId(userId)
            val responseList = cartList.map { it.toResponse() }
            call.respond(HttpStatusCode.OK, responseList)

        }

        put("/{cartId}") {
            val cartId = call.parameters["cartId"] ?: return@put call.respond(
                HttpStatusCode.BadRequest,
                "Cart ID is required"
            )
            val updatedCart = call.receive<CartUpdateRequest>()
            val updateModel = updatedCart.toDomain()
            val wasUpdated = cartService.updateCart(cartId, updateModel)
            if (wasUpdated) {
                call.respond(HttpStatusCode.OK, "Cart updated successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to update cart")
            }
        }

        delete("/{cartId}") {
            val cartId = call.parameters["cartId"] ?: return@delete call.respond(
                HttpStatusCode.BadRequest,
                "Cart ID is required"
            )
            val wasDeleted = cartService.deleteCart(cartId)

            if (wasDeleted) {
                call.respond(HttpStatusCode.OK, "Cart deleted successfully")
            } else {
                call.respond(HttpStatusCode.InternalServerError, "Failed to delete cart")
            }
        }
    }
}