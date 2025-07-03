package com.commerce.presentation.routes

import com.commerce.data.product.service.ProductServiceImpl
import domain.product.usecase.ProductUseCase
import io.ktor.server.routing.*
import presentation.controllers.ProductController

//fun Route.productRoutes(productService: ProductService) {
//    route("/products") {
//
//        // Health or basic test route
//        get("/ping") {
//            call.respond(mapOf("message" to "Products route is working"))
//        }
//
//        // Add a new product (Authenticated user)
//        post {
//            val principal = call.principal<JWTPrincipal>()
//            val userId = principal?.getClaim("userId", String::class)
//
//            if (userId == null) {
//                call.respond(HttpStatusCode.Unauthorized, "Missing or invalid token")
//                return@post
//            }
//
//            val productRequest = call.receive<ProductRequest>()
//            val productEntity = productRequest.toDomain(sellerId = userId)
//            val result = productService.insertProduct(productEntity)
//            call.respond(HttpStatusCode.Created, mapOf("productId" to result))
//        }
//
//        // Get all products
//        get {
//            val productList = productService.getAllProducts()
//            val responseList = productList.map { it.toResponse() }
//            call.respond(HttpStatusCode.OK, responseList)
//        }
//
//        // Search product by name
//        get("/search/{name}") {
//            val productName = call.parameters["name"] ?: return@get call.respond(
//                HttpStatusCode.BadRequest,
//                "Product name is required"
//            )
//            val productList = productService.findProductByName(productName)
//            val responseList = productList.map { it.toResponse() }
//            call.respond(HttpStatusCode.OK, responseList)
//        }
//
//        // Update entire product
//        put("/{productId}") {
//            val productId = call.parameters["productId"] ?: return@put call.respond(
//                HttpStatusCode.BadRequest,
//                "Product ID is required"
//            )
//
//            val request = call.receive<ProductUpdateRequest>()
//            val updateModel = request.toDomain()
//
//            val wasUpdated = productService.updateProductById(productId, updateModel)
//
//            if (wasUpdated) {
//                call.respond(HttpStatusCode.OK, "Product updated successfully")
//            } else {
//                call.respond(HttpStatusCode.NotFound, "Product not found")
//            }
//        }
//
//        // Partial update (if needed)
////        patch("/{productId}") {
////            val productId = call.parameters["productId"] ?: return@patch call.respond(
////                HttpStatusCode.BadRequest,
////                "Product ID is required"
////            )
////
////            val request = call.receive<ProductUpdateRequest>()
////            val updateModel = request.toDomain()
////            val wasUpdated = productService.updateProductById(productId, updateModel)
////
////            if (wasUpdated) {
////                call.respond(HttpStatusCode.OK, "Product partially updated successfully")
////            } else {
////                call.respond(HttpStatusCode.NotFound, "Product not found")
////            }
////        }
//
//        // Delete product
//        delete("/{productId}") {
//            val productId = call.parameters["productId"] ?: return@delete call.respond(
//                HttpStatusCode.BadRequest,
//                "Product ID is required"
//            )
//
//            val wasDeleted = productService.deleteProductById(productId)
//
//            if (wasDeleted) {
//                call.respond(HttpStatusCode.OK, "Product deleted successfully")
//            } else {
//                call.respond(HttpStatusCode.NotFound, "Product not found")
//            }
//        }
//    }
//}


fun Route.productRoutes(productUseCase: ProductUseCase) {
    val controller = ProductController(productUseCase)

    route("/products") {
        get("/ping") { controller.ping(call) }

        post { controller.insertProduct(call) }
        get { controller.getAllProducts(call) }
        get("/search/{name}") { controller.searchProductByName(call) }
        put("/{productId}") { controller.updateProduct(call) }
        delete("/{productId}") { controller.deleteProduct(call) }

    }
}