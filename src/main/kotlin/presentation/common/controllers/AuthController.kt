package com.commerce.presentation.common.controllers

import com.commerce.config.JwtConfig
import com.commerce.data.common.auth.dto.UserRequest
import com.commerce.data.common.auth.mapper.toDomain
import com.commerce.domain.common.auth.usecase.AuthUseCase
import domain.common.auth.model.GoogleUserInfo
import domain.common.auth.model.UserRole
import io.ktor.server.application.*
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import utils.fetchGoogleUserInfo

/**
 * Controller class responsible for handling authentication-related operations.
 * Handles user registration (sign up), role-based login, and Google OAuth login.
 *
 * @param authUseCase Contains use cases for registering and logging in a user.
 */
class AuthController(private val authUseCase: AuthUseCase) {

    /**
     * Handles user registration via traditional email/password.
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
     * Handles login for User, Seller, and Admin roles.
     * Generates JWT token if login succeeds.
     */
    suspend fun login(call: ApplicationCall, role: UserRole) {
        val request = call.receive<UserRequest>()        // Parse login credentials
        val user = request.toDomain()                    // Convert DTO to domain model
        val userId = authUseCase.loginUseCase(user)      // Authenticate user

        if (userId != null) {
            val token = JwtConfig.generateToken(userId, role) // Generate JWT with userId + role
            call.respond(HttpStatusCode.OK, mapOf("token" to token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }

    /**
     * Handles Google OAuth Sign-in or Sign-up.
     * - Extracts OAuth token from Google
     * - Fetches user info from Google API
     * - Returns structured user info (or optionally logs in/registers in your system)
     */
    suspend fun googleSignUp(call: ApplicationCall) {
        val principal = call.principal<OAuthAccessTokenResponse.OAuth2>()
        val accessToken = principal?.accessToken

        if (accessToken == null) {
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "OAuth failed."))
            return
        }

        try {
            // Fetch Google user info using reusable HttpClient
            val userInfo = fetchGoogleUserInfo(accessToken)

            // TODO: Check DB if user exists → If not, register → If exists, return token
            // val jwt = JwtConfig.generateToken(userInfo.id, UserRole.USER)
            // call.respond(HttpStatusCode.OK, mapOf("token" to jwt))

            // Temporary response (raw Google user info)
            call.respond(HttpStatusCode.OK, userInfo)

        } catch (e: Exception) {
            e.printStackTrace()
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to "Failed to fetch user info")
            )
        }
    }
}
