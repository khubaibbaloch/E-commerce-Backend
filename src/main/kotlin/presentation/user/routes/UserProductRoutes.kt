package com.commerce.presentation.user.routes

import domain.user.product.usecase.UserProductUseCase
import io.ktor.server.routing.*
import com.commerce.presentation.user.controllers.UserProductController


fun Route.userProductRoutes(userProductUseCase: UserProductUseCase) {
    val controller = UserProductController(userProductUseCase)
    route("/products") {

        get("/ping") { controller.ping(call) }
        //post { controller.insertProduct(call) }
        get { controller.getAllProducts(call) }
        get("/search/{name}") { controller .searchProductByName(call) }
        //put("/{productId}") { controller.updateProduct(call) }
        //delete("/{productId}") { controller.deleteProduct(call) }

    }
}