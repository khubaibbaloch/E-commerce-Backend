package com.commerce.presentation.user.controllers

import com.commerce.data.user.product.mapper.toResponse
import domain.user.product.usecase.UserProductUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/**
 * Controller for handling product-related requests from users.
 * Provides routes for ping, getting all products, and searching by name.
 */
class UserProductController(
    private val userProductUseCase: UserProductUseCase
) {

    /**
     * Simple endpoint to verify the product routes are working.
     * Useful for debugging or initial route check.
     */
    suspend fun ping(call: ApplicationCall) {
        call.respond(mapOf("message" to "Products route is working"))
    }

    /**
     * Retrieves all available products in the system.
     * Response: 200 OK with a list of ProductResponse DTOs.
     */
    suspend fun getAllProducts(call: ApplicationCall) {
        val productList = userProductUseCase.getAll()
        call.respond(HttpStatusCode.OK, productList.map { it.toResponse() })
    }

    /**
     * Searches for products by name.
     * Query parameter: `name`
     * Returns 400 if name is not provided.
     * Response: 200 OK with a list of matching ProductResponse DTOs.
     */
    suspend fun searchProductByName(call: ApplicationCall) {
        val productName = call.parameters["name"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Product name is required")

        val productList = userProductUseCase.findByName(productName)
        call.respond(HttpStatusCode.OK, productList.map { it.toResponse() })
    }
}
