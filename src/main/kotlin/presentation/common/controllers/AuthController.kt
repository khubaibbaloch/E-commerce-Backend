package com.commerce.presentation.common.controllers

import com.commerce.config.JwtConfig
import com.commerce.data.common.auth.dto.UserRequest
import com.commerce.data.common.auth.mapper.toDomain
import com.commerce.domain.common.auth.usecase.AuthUseCase
import domain.common.auth.model.UserRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

/**
 * Controller class responsible for handling authentication-related operations.
 * Handles user registration (sign up) and login functionality.
 *
 * @param authUseCase Contains use cases for registering and logging in a user.
 */
class AuthController(private val authUseCase: AuthUseCase) {

    /**
     * Handles user registration.
     * - Receives the request payload as a UserRequest.
     * - Converts it to a domain model using `toDomain()`.
     * - Calls the `registerUseCase` to store user in DB.
     * - Responds with appropriate message based on registration success/failure.
     */
    suspend fun signUp(call: ApplicationCall) {
        val request = call.receive<UserRequest>()        // Parse request body
        val user = request.toDomain()                    // Convert DTO to domain model
        val userId = authUseCase.registerUseCase(user)   // Try registering user

        if (userId != null) {
            call.respond(
                HttpStatusCode.OK,
                mapOf("message" to "Account created. Please login.")
            )
        } else {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        }
    }

    /**
     * Handles user login.
     * - Receives the login request payload.
     * - Verifies user credentials using `loginUseCase`.
     * - If valid, generates a JWT token with user ID and role.
     * - Returns token to client on success, else responds with error.
     *
     * @param role The role to be embedded in the JWT token (e.g., USER, SELLER, ADMIN)
     */
    suspend fun login(call: ApplicationCall, role: UserRole) {
        val request = call.receive<UserRequest>()        // Parse login credentials
        val user = request.toDomain()                    // Convert DTO to domain model
        val userId = authUseCase.loginUseCase(user)      // Authenticate user

        if (userId != null) {
            val token = JwtConfig.generateToken(userId, role) // Generate JWT with role
            call.respond(HttpStatusCode.OK, mapOf("token" to token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }
}
