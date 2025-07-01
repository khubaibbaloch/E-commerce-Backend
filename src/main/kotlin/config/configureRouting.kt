package com.commerce.config

import com.commerce.data.dto.auth.TokenResponse
import com.commerce.presentation.routes.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val authService = authService
    val productService = productService
    val cartService = cartService
    val orderService = orderService
    val paymentService = paymentService
    routing {
        authRoutes(authService)
        get("/") {
            val token = JwtConfig.generateToken("khubaib")
            call.respond(TokenResponse(token))
        }

        authenticate("auth-jwt") {
            productRoutes(productService)
            cartRoutes(cartService)
            orderRoutes(orderService)
            paymentRoutes(paymentService)
        }
    }
}
