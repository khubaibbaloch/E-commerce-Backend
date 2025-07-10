package com.commerce.presentation.seller.routes

import com.commerce.domain.seller.usecase.SellerProductUseCase
import com.commerce.presentation.seller.controllers.SellerProductController
import io.ktor.server.routing.*

/**
 * Defines all routes for seller-related product operations.
 *
 * Base route: /product
 * Operations:
 * - GET /ping                  → Health check for the product route
 * - POST /                    → Insert a new product (requires seller auth)
 * - PUT /{productId}          → Update product by ID
 * - DELETE /{productId}       → Delete product by ID
 */
fun Route.sellerProductRoutes(sellerProductUseCase: SellerProductUseCase) {

    // Initialize the controller with use cases
    val controller = SellerProductController(sellerProductUseCase)

    // Group all product-related endpoints under /product
    route("/product") {

        // Simple GET to test the route is working
        get("/ping") {
            controller.ping(call)
        }

        // POST /product
        // Creates a new product
        post {
            controller.insertProduct(call)
        }

        // PUT /product/{productId}
        // Updates a product with the given ID
        put("/{productId}") {
            controller.updateProduct(call)
        }

        // DELETE /product/{productId}
        // Deletes a product by ID
        delete("/{productId}") {
            controller.deleteProduct(call)
        }
    }
}
