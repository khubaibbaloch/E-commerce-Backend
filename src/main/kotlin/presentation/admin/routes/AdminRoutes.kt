package com.commerce.presentation.admin.routes

import com.commerce.domain.admin.usecase.AdminUseCase
import com.commerce.presentation.admin.controllers.AdminController
import io.ktor.server.routing.*

/**
 * Defines the routing for admin-related API endpoints.
 * Registers HTTP GET routes for fetching all users and products.
 *
 * @param adminUseCase The use case class that provides admin operations.
 */
fun Route.adminRoutes(adminUseCase: AdminUseCase) {
    // Create an instance of the AdminController to handle requests
    val controller = AdminController(adminUseCase)

    // GET /users → Fetch all registered users (Admin access)
    get("/users") {
        controller.getAllUsers(call)
    }

    // GET /products → Fetch all products in the system (Admin access)
    get("/products") {
        controller.getAllProducts(call)
    }
}
