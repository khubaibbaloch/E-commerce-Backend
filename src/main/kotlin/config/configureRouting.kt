package com.commerce.config

import com.commerce.domain.admin.usecase.AdminUseCase
import com.commerce.domain.auth.usecase.AuthUseCase
import com.commerce.domain.cart.usecase.CartUseCase
import com.commerce.domain.order.usecase.OrderUseCase
import com.commerce.presentation.admin.routes.adminRoutes
import com.commerce.presentation.common.routes.authRoutes
import com.commerce.presentation.user.routes.cartRoutes
import com.commerce.presentation.user.routes.orderRoutes
import com.commerce.presentation.user.routes.paymentRoutes
import com.commerce.presentation.user.routes.userProductRoutes
import com.commerce.utils.roleBasedRoute
import domain.common.auth.model.UserRole
import domain.user.payment.usecase.PaymentUseCase
import domain.user.product.usecase.UserProductUseCase
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.response.respond
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

fun Application.configureRouting() {
//    val authService = authServiceImpl
//    val productService = productServiceImpl
//    val cartService = cartServiceImpl
//    val orderService = orderServiceImpl
//    val paymentService = paymentServiceImpl

    val authUseCase by inject<AuthUseCase>()
    val userProductUseCase by inject<UserProductUseCase>()
    val cartUseCase by inject<CartUseCase>()
    val orderUseCase by inject<OrderUseCase>()
    val paymentUseCase by inject<PaymentUseCase>()
    val adminUseCase by inject<AdminUseCase>()
    val logger = LoggerFactory.getLogger("RoutingLogger")

    routing {

        authRoutes(authUseCase)

        authenticate("auth-jwt") {


            route("/user") {
                route("/ping") {
                    get {
                        val principal = call.principal<JWTPrincipal>("auth-jwt")
                        val role = principal?.payload?.getClaim("role")?.asString()

                        call.respond(mapOf("result" to "$role"))
                        println("JWT role from token: '$role'")
                    }
                }
                roleBasedRoute(UserRole.USER) {
                    userProductRoutes(userProductUseCase)
                    cartRoutes(cartUseCase)
                    orderRoutes(orderUseCase)
                    paymentRoutes(paymentUseCase)
                }
            }


//                route("/seller") {
//                    roleBasedRoute(UserRole.SELLER) {
//                        get("/ping") { call.respond(mapOf("Ping" to "Working")) }
//                }
//            }
//
//
//                route("/admin") {
//                    roleBasedRoute(UserRole.ADMIN) {
//                    adminRoutes(adminUseCase)
//                }
//            }

        }
    }
}
