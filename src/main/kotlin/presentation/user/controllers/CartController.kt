package com.commerce.presentation.user.controllers

import com.commerce.data.user.cart.dto.CartRequest
import com.commerce.data.user.cart.dto.CartUpdateRequest
import com.commerce.data.user.cart.mapper.toDomain
import com.commerce.data.user.cart.mapper.toResponse
import com.commerce.domain.user.cart.usecase.CartUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*

/**
 * Controller to handle user cart-related actions like:
 * - Add item to cart
 * - Get user cart
 * - Update cart item
 * - Delete cart item
 */
class CartController(
    private val cartUseCase: CartUseCase
) {

    /**
     * Adds a product to the user's cart.
     * Requires a valid JWT token with `userId` claim.
     * Body: CartRequest (productId, quantity)
     */
    suspend fun addCart(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
        if (userId == null) {
            call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")
            return
        }

        val request = call.receive<CartRequest>()
        val entity = request.toDomain(userId)
        val result = cartUseCase.addCart(entity)
        call.respond(HttpStatusCode.Created, mapOf("cartId" to result))
    }

    /**
     * Retrieves the current user's cart with product details.
     * Requires a valid JWT token with `userId` claim.
     * Returns a list of cart items.
     */
    suspend fun getCart(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
        if (userId == null) {
            call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")
            return
        }

        val cartList = cartUseCase.findCartByUserId(userId)
        call.respond(HttpStatusCode.OK, cartList.map { it.toResponse() })
    }

    /**
     * Updates quantity of a cart item.
     * Path param: cartId
     * Body: CartUpdateRequest (quantity)
     */
    suspend fun updateCart(call: ApplicationCall) {
        val cartId = call.parameters["cartId"]
        if (cartId == null) {
            call.respond(HttpStatusCode.BadRequest, "Cart ID is required")
            return
        }

        val updateRequest = call.receive<CartUpdateRequest>()
        val updatedModel = updateRequest.toDomain()

        val wasUpdated = cartUseCase.updateCart(cartId, updatedModel)
        if (wasUpdated) {
            call.respond(HttpStatusCode.OK, "Cart updated successfully")
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Failed to update cart")
        }
    }

    /**
     * Deletes a cart item by its ID.
     * Path param: cartId
     */
    suspend fun deleteCart(call: ApplicationCall) {
        val cartId = call.parameters["cartId"]
        if (cartId == null) {
            call.respond(HttpStatusCode.BadRequest, "Cart ID is required")
            return
        }

        val wasDeleted = cartUseCase.deleteCart(cartId)
        if (wasDeleted) {
            call.respond(HttpStatusCode.OK, "Cart deleted successfully")
        } else {
            call.respond(HttpStatusCode.InternalServerError, "Failed to delete cart")
        }
    }

    /**
     * Health check endpoint for the cart route.
     * Can be used to confirm routing works.
     */
    suspend fun ping(call: ApplicationCall) {
        call.respond(mapOf("message" to "Cart route is working"))
    }
}
