package com.commerce.config


import com.commerce.domain.admin.usecase.AdminUseCase
import com.commerce.domain.common.auth.usecase.AuthUseCase
import com.commerce.domain.user.cart.usecase.CartUseCase
import com.commerce.domain.order.usecase.OrderUseCase
import com.commerce.domain.seller.usecase.SellerProductUseCase
import com.commerce.presentation.admin.routes.adminRoutes
import com.commerce.presentation.common.routes.authRoutes
import com.commerce.presentation.seller.routes.sellerProductRoutes
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
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.slf4j.LoggerFactory

/**
 * Main Ktor routing configuration.
 * Defines all endpoint groups, applies authentication, and restricts access based on roles.
 */
fun Application.configureRouting() {
    // Legacy commented-out service references (previous manual wiring)
    // val authService = authServiceImpl
    // val productService = productServiceImpl
    // val cartService = cartServiceImpl
    // val orderService = orderServiceImpl
    // val paymentService = paymentServiceImpl

    // Injecting business use cases via Koin DI
    val authUseCase by inject<AuthUseCase>()
    val userProductUseCase by inject<UserProductUseCase>()
    val cartUseCase by inject<CartUseCase>()
    val orderUseCase by inject<OrderUseCase>()
    val paymentUseCase by inject<PaymentUseCase>()
    val adminUseCase by inject<AdminUseCase>()
    val sellerProductUseCase by inject<SellerProductUseCase>()
    val logger = LoggerFactory.getLogger("RoutingLogger") // For route-level logging

    routing {

        // Public routes (no auth required)
        authRoutes(authUseCase)

        // Authenticated routes (JWT required)
        authenticate("auth-jwt") {

            // All endpoints under /user require USER role
            route("/user") {
                roleBasedRoute(UserRole.USER) {
                    userProductRoutes(userProductUseCase) // User browsing and product interaction
                    cartRoutes(cartUseCase)               // User shopping cart operations
                    orderRoutes(orderUseCase)             // User order management
                    paymentRoutes(paymentUseCase)         // Payment handling for users
                }
            }

            // All endpoints under /seller require SELLER role
            route("/seller") {
                // Protect this section with SELLER role only
                roleBasedRoute(UserRole.SELLER) {
                    sellerProductRoutes(sellerProductUseCase) // Manage seller product listings
                }
            }

            // All endpoints under /admin require ADMIN role
            route("/admin") {
                roleBasedRoute(UserRole.ADMIN) {
                    adminRoutes(adminUseCase) // Admin features like approving sellers, dashboards
                }
            }

        }
    }
}
