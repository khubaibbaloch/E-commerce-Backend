package presentation.controllers

import com.commerce.data.product.dto.ProductRequest
import com.commerce.data.product.dto.ProductUpdateRequest
import com.commerce.data.product.mapper.toDomain
import com.commerce.data.product.mapper.toResponse
import com.commerce.data.product.service.ProductServiceImpl
import domain.product.usecase.ProductUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ProductController(
    private val productUseCase: ProductUseCase
) {

    suspend fun ping(call: ApplicationCall) {
        call.respond(mapOf("message" to "Products route is working"))
    }

    suspend fun insertProduct(call: ApplicationCall) {
        val userId = call.principal<JWTPrincipal>()?.getClaim("userId", String::class)
            ?: return call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")

        val request = call.receive<ProductRequest>()
        val productEntity = request.toDomain(sellerId = userId)
        val result = productUseCase.insert(productEntity)
        call.respond(HttpStatusCode.Created, mapOf("productId" to result))
    }

    suspend fun getAllProducts(call: ApplicationCall) {
        val productList = productUseCase.getAll()
        call.respond(HttpStatusCode.OK, productList.map { it.toResponse() })
    }

    suspend fun searchProductByName(call: ApplicationCall) {
        val productName = call.parameters["name"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Product name is required")

        val productList = productUseCase.findByName(productName)
        call.respond(HttpStatusCode.OK, productList.map { it.toResponse() })
    }

    suspend fun updateProduct(call: ApplicationCall) {
        val productId = call.parameters["productId"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Product ID is required")

        val request = call.receive<ProductUpdateRequest>()
        val updateModel = request.toDomain()
        val wasUpdated = productUseCase.updateById(productId, updateModel)

        if (wasUpdated) {
            call.respond(HttpStatusCode.OK, "Product updated successfully")
        } else {
            call.respond(HttpStatusCode.NotFound, "Product not found")
        }
    }

    suspend fun deleteProduct(call: ApplicationCall) {
        val productId = call.parameters["productId"]
            ?: return call.respond(HttpStatusCode.BadRequest, "Product ID is required")

        val wasDeleted = productUseCase.deleteById(productId)

        if (wasDeleted) {
            call.respond(HttpStatusCode.OK, "Product deleted successfully")
        } else {
            call.respond(HttpStatusCode.NotFound, "Product not found")
        }
    }
}
