package com.commerce.presentation.user.routes

import domain.user.product.usecase.UserProductUseCase
import io.ktor.server.routing.*
import com.commerce.presentation.user.controllers.UserProductController

/**
 * Defines routes for users to interact with available products.
 * These routes allow users to retrieve all products or search for a product by name.
 *
 * Base path: /products
 */
fun Route.userProductRoutes(userProductUseCase: UserProductUseCase) {
    val controller = UserProductController(userProductUseCase)

    route("/products") {

        // GET /products/ping - Health check route for debugging
        get("/ping") { controller.ping(call) }

        // GET /products - Fetch all available products from the database
        get { controller.getAllProducts(call) }

        // GET /products/search/{name} - Search for products by their name
        get("/search/{name}") { controller.searchProductByName(call) }

        // Note: The following routes are commented out because they're typically for sellers/admins.
        // Uncomment and secure with appropriate role-based access if needed.

        // POST /products - Add a new product (likely for seller)
        // post { controller.insertProduct(call) }

        // PUT /products/{productId} - Update a product by its ID
        // put("/{productId}") { controller.updateProduct(call) }

        // DELETE /products/{productId} - Delete a product by its ID
        // delete("/{productId}") { controller.deleteProduct(call) }
    }
}
