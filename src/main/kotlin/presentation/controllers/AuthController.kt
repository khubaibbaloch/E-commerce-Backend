package com.commerce.presentation.controllers

import com.commerce.config.JwtConfig
import com.commerce.data.auth.dto.TokenResponse
import com.commerce.data.auth.dto.UserRequest
import com.commerce.data.auth.mapper.toDomain
import com.commerce.data.auth.service.AuthServiceImpl
import com.commerce.domain.auth.usecase.AuthUseCase
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
            val token = JwtConfig.generateToken(userId)
            call.respond(HttpStatusCode.OK, TokenResponse(token))
        } else {
            call.respond(HttpStatusCode.Conflict, "User already exists")
        }
    }

    suspend fun login(call: ApplicationCall) {
        val request = call.receive<UserRequest>()
        val user = request.toDomain()
        val userId = authUseCase.loginUseCase(user)

        if (userId != null) {
            val token = JwtConfig.generateToken(userId)
            call.respond(HttpStatusCode.OK, TokenResponse(token))
        } else {
            call.respond(HttpStatusCode.Unauthorized, "Invalid username or password")
        }
    }
}
