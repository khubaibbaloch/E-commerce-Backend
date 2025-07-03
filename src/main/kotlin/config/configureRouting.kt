package com.commerce.config

import com.commerce.data.auth.dto.TokenResponse
import com.commerce.domain.auth.usecase.AuthUseCase
import com.commerce.domain.cart.usecase.CartUseCase
import com.commerce.domain.order.usecase.OrderUseCase
import com.commerce.presentation.routes.*
import domain.payment.usecase.PaymentUseCase
import domain.product.usecase.ProductUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
//    val authService = authServiceImpl
//    val productService = productServiceImpl
//    val cartService = cartServiceImpl
//    val orderService = orderServiceImpl
//    val paymentService = paymentServiceImpl

    val authUseCase by inject<AuthUseCase>()
    val productUseCase by inject<ProductUseCase>()
    val cartUseCase by inject<CartUseCase>()
    val orderUseCase by inject<OrderUseCase>()
    val paymentUseCase by inject<PaymentUseCase>()

    routing {
        authRoutes(authUseCase)
        get("/") {
            val token = JwtConfig.generateToken("khubaib")
            call.respond(TokenResponse(token))
        }

        authenticate("auth-jwt") {
            productRoutes(productUseCase)
            cartRoutes(cartUseCase)
            orderRoutes(orderUseCase)
            paymentRoutes(paymentUseCase)
        }
    }
}
