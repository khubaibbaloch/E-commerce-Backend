package com.commerce.presentation.common.controllers

import com.commerce.config.JwtConfig
import com.commerce.data.auth.dto.TokenResponse
import com.commerce.data.auth.dto.UserRequest
import com.commerce.data.auth.mapper.toDomain
import com.commerce.domain.auth.usecase.AuthUseCase
import domain.common.auth.model.UserRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*


class AuthController(private val authUseCase: AuthUseCase) {


    suspend fun signUp(call: ApplicationCall) {
        val request = call.receive<UserRequest>()
        val user = request.toDomain()
        val userId = authUseCase.registerUseCase(user)

        if (userId != null) {
            call.respond(HttpStatusCode.OK, mapOf("message" to "Account created. Please login."))
        } else {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        }
    }

    suspend fun login(call: ApplicationCall) {
        val request = call.receive<UserRequest>()
        val user = request.toDomain()
        val userId = authUseCase.loginUseCase(user)
        if (userId != null) {
            val token = JwtConfig.generateToken(userId,UserRole.USER)
            call.respond(HttpStatusCode.OK, TokenResponse(token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
        }
    }
}
