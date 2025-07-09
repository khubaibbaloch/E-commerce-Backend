package com.commerce.presentation.admin.routes

import com.commerce.domain.admin.usecase.AdminUseCase
import com.commerce.presentation.admin.controllers.AdminController
import io.ktor.server.routing.*

fun Route.adminRoutes(adminUseCase: AdminUseCase) {
    val controller = AdminController(adminUseCase)
    get("/users") {
        controller.getAllUsers(call)
    }
    get("/products") {
        controller.getAllProducts(call)
    }
}