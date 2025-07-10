package com.commerce.presentation.admin.controllers

import com.commerce.data.admin.mapper.toResponse
import com.commerce.data.user.product.mapper.toResponse
import com.commerce.domain.admin.usecase.AdminUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

/**
 * Controller responsible for handling admin-related HTTP requests.
 * It uses AdminUseCase to interact with the domain logic.
 */
class AdminController(private val adminUseCase: AdminUseCase) {

    /**
     * Handles the GET request to retrieve all users.
     * - Calls the use case to fetch all users from the system.
     * - Maps domain models to DTOs using `toResponse()`.
     * - Responds with HTTP 200 OK and the list of users in JSON format.
     */
    suspend fun getAllUsers(call: ApplicationCall){
        val allUsers = adminUseCase.getAllUser()               // Fetch users from domain
        val response = allUsers.map { it.toResponse() }        // Map to DTOs for response
        call.respond(HttpStatusCode.OK, response)              // Send HTTP 200 with users
    }

    /**
     * Handles the GET request to retrieve all products.
     * - Calls the use case to fetch all products managed by sellers.
     * - Maps domain models to DTOs using `toResponse()`.
     * - Responds with HTTP 200 OK and the list of products in JSON format.
     */
    suspend fun getAllProducts(call: ApplicationCall){
        val allProducts = adminUseCase.getAllProducts()         // Fetch products from domain
        val response = allProducts.map { it.toResponse() }      // Map to DTOs for response
        call.respond(HttpStatusCode.OK, response)               // Send HTTP 200 with products
    }
}
