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

    suspend fun login(call: ApplicationCall, role: UserRole) {
        val request = call.receive<UserRequest>()
        val user = request.toDomain()
        val userId = authUseCase.loginUseCase(user)

        if (userId != null) {
            val token = JwtConfig.generateToken(userId, role)
            call.respond(HttpStatusCode.OK, mapOf("token" to token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid credentials")
        }
    }
}
