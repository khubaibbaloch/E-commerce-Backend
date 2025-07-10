package com.commerce.presentation.user.routes

import com.commerce.domain.user.cart.usecase.CartUseCase
import io.ktor.server.routing.*
import com.commerce.presentation.user.controllers.CartController

/**
 * Defines all the routes related to user's cart operations.
 * This includes adding, retrieving, updating, and deleting cart items.
 *
 * Base path: /carts
 */
fun Route.cartRoutes(cartUseCase: CartUseCase) {
    // Initialize the controller with the required use case
    val controller = CartController(cartUseCase)

    // Group all routes under "/carts" path
    route("/carts") {

        // Health check endpoint for cart routes
        get("/ping") { controller.ping(call) }

        // Add a new product to the user's cart
        post { controller.addCart(call) }

        // Retrieve all cart items for the authenticated user
        get { controller.getCart(call) }

        // Update quantity for a specific cart item
        put("/{cartId}") { controller.updateCart(call) }

        // Remove a specific item from the cart
        delete("/{cartId}") { controller.deleteCart(call) }
    }
}
