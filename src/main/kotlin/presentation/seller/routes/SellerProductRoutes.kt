package com.commerce.presentation.seller.routes

import com.commerce.domain.seller.usecase.SellerProductUseCase
import com.commerce.presentation.seller.controllers.SellerProductController
import com.commerce.presentation.user.controllers.UserProductController
import domain.user.product.usecase.UserProductUseCase
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.sellerProductRoutes(sellerProductUseCase: SellerProductUseCase) {
    val controller = SellerProductController(sellerProductUseCase)
    route("/products") {
        get("/ping") { controller.ping(call) }
        post { controller.insertProduct(call) }
        put("/{productId}") { controller.updateProduct(call) }
        delete("/{productId}") { controller.deleteProduct(call) }

    }
}