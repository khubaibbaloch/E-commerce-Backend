package com.commerce.presentation.routes

import com.commerce.config.JwtConfig
import com.commerce.data.dto.auth.TokenResponse
import com.commerce.domain.services.AuthService
import com.commerce.data.dto.auth.UserRequest
import com.commerce.data.dto.auth.toDomain
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.response.respond


fun Route.authRoutes(authService: AuthService) {
    route("/auth") {

        post("/signUp") {
            val userRequest = call.receive<UserRequest>()
            val userEntity = userRequest.toDomain()
            val userId = authService.registerAndReturnUserId(userEntity)

            if (userId != null) {
                val token = JwtConfig.generateToken(userId)
                val response = TokenResponse(token)
                call.respond(HttpStatusCode.OK, response)
            } else {
                call.respond(HttpStatusCode.Conflict, "User already exists")
            }
        }

        post("/login") {
            val userRequest = call.receive<UserRequest>()
            val userEntity = userRequest.toDomain()
            val userId = authService.login(userEntity)

            if (userId != null) {
                val token = JwtConfig.generateToken(userId)
                val response = TokenResponse(token)
                call.respond(HttpStatusCode.OK, response)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
            }
        }
    }
}
