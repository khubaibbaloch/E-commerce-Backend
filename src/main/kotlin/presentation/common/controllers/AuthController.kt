package com.commerce.presentation.common.controllers

import com.commerce.config.JwtConfig
import com.commerce.data.common.auth.dto.UserRequest
import com.commerce.data.common.auth.mapper.toDomain
import com.commerce.domain.common.auth.usecase.AuthUseCase
import domain.common.auth.model.UserRole
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.apache.Apache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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

    suspend fun googleSignUp(call: ApplicationCall){
        val principal = call.principal<OAuthAccessTokenResponse.OAuth2>()
        val accessToken = principal?.accessToken

        if (accessToken != null) {
            val client = HttpClient(Apache) {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint =
                            true   // Formats the JSON output for readability (useful during development)
                        isLenient =
                            true     // Allows parsing of non-strict JSON (e.g., missing quotes or unquoted keys)
                        ignoreUnknownKeys = true
                    })
                }
            }

            // 🔄 Call Google to get user info
            val userInfo: GoogleUserInfo = client.get("https://www.googleapis.com/oauth2/v2/userinfo") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $accessToken")
                }
            }.body()


            // 🎯 Handle sign-up or login by email
            val user = userInfo

            // 🎫 Issue your app’s JWT for the user
             //val jwt = JwtConfig.generateToken(user.id, UserRole.USER)
            call.respond(mapOf("id" to user.id))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "OAuth failed.")
        }
    }
}
@Serializable
data class GoogleUserInfo(
    val id: String,
    val email: String,
    val verified_email: Boolean,
    val name: String,
    val given_name: String,
    val family_name: String,
    val picture: String,
)