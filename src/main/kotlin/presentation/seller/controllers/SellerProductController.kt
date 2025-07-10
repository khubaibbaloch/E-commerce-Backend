package com.commerce.presentation.seller.controllers

import com.commerce.data.seller.dto.ProductRequest
import com.commerce.data.seller.dto.ProductUpdateRequest
import com.commerce.data.seller.mapper.toDomain
import com.commerce.domain.seller.usecase.SellerProductUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond

/**
 * Handles seller-side product operations like create, update, delete.
 */
class SellerProductController(
    private val sellerProductUseCase: SellerProductUseCase
) {

    /**
     * Test route to ensure the product controller is working.
     */
    suspend fun ping(call: ApplicationCall) {
        call.respond(mapOf("message" to "Products route is working"))
    }

    /**
     * Inserts a new product for the authenticated seller.
     *
     * Steps:
     * 1. Extracts seller's userId from JWT token.
     * 2. Receives product request body.
     * 3. Converts DTO to domain entity.
     * 4. Calls use case to insert the product.
     * 5. Returns the new product ID.
     */
    suspend fun insertProduct(call: ApplicationCall) {
        // Get seller ID from JWT
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")

        // Parse request body
        val request = call.receive<ProductRequest>()

        // Map to domain model with seller ID
        val productEntity = request.toDomain(sellerId = userId)

        // Insert product and return ID
        val result = sellerProductUseCase.insert(productEntity)
        call.respond(HttpStatusCode.Created, mapOf("productId" to result))
    }

    /**
     * Updates an existing product.
     *
     * Steps:
     * 1. Gets productId from route parameters.
     * 2. Receives updated product data.
     * 3. Converts DTO to domain update model.
     * 4. Calls use case to update the product.
     * 5. Responds with success/failure status.
     */
    suspend fun updateProduct(call: ApplicationCall) {
        val productId = call.parameters["productId"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Product ID is required")

        val request = call.receive<ProductUpdateRequest>()
        val updateModel = request.toDomain()

        val wasUpdated = sellerProductUseCase.updateById(productId, updateModel)

        if (wasUpdated) {
            call.respond(HttpStatusCode.OK, "Product updated successfully")
        } else {
            call.respond(HttpStatusCode.NotFound, "Product not found")
        }
    }

    /**
     * Deletes an existing product by its ID.
     *
     * Steps:
     * 1. Gets productId from route parameters.
     * 2. Calls use case to delete the product.
     * 3. Responds with success/failure message.
     */
    suspend fun deleteProduct(call: ApplicationCall) {
        val productId = call.parameters["productId"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Product ID is required")

        val wasDeleted = sellerProductUseCase.deleteById(productId)

        if (wasDeleted) {
            call.respond(HttpStatusCode.OK, "Product deleted successfully")
        } else {
            call.respond(HttpStatusCode.NotFound, "Product not found")
        }
    }
}
