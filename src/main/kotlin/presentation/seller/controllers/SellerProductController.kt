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

class SellerProductController(
    private val sellerProductUseCase: SellerProductUseCase
) {

    suspend fun ping(call: ApplicationCall) {
        call.respond(mapOf("message" to "Products route is working"))
    }

    suspend fun insertProduct(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")

        val request = call.receive<ProductRequest>()
        val productEntity = request.toDomain(sellerId = userId)
        val result = sellerProductUseCase.insert(productEntity)
        call.respond(HttpStatusCode.Created, mapOf("productId" to result))
    }

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
