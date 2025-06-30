package com.commerce.presentation.routes

import com.commerce.config.JwtConfig
import com.commerce.data.dto.auth.TokenResponse
import com.commerce.data.dto.product.ProductRequest
import com.commerce.data.dto.product.toDomain
import com.commerce.data.dto.product.toResponse
import com.commerce.domain.services.ProductService
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes(productService: ProductService) {
    route("/products") {

        get("/") {
            call.respond(mapOf("products" to "working"))
        }

        post("/add") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.getClaim("userId", String::class)

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")
                return@post
            }

            val productRequest = call.receive<ProductRequest>()
            val productEntity = productRequest.toDomain(sellerId = userId)
            val result = productService.insertProduct(productEntity)
            call.respond(HttpStatusCode.Created, mapOf("productId" to result))
        }

        get("/{name}") {
            val productName = call.parameters["name"] ?: return@get call.respond(
                HttpStatusCode.BadRequest,
                "Product name is required"
            )

            val productList = productService.findProductByName(productName)
            val responseList = productList.map { it.toResponse() }
            call.respond(HttpStatusCode.OK, responseList)
        }
    }
}
