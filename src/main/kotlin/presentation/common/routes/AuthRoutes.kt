package com.commerce.presentation.common.routes

import com.commerce.domain.common.auth.usecase.AuthUseCase
import com.commerce.presentation.common.controllers.AuthController
import domain.common.auth.model.UserRole
import io.ktor.server.routing.*

/**
 * Defines routing for authentication-related endpoints (sign up and login).
 * Supports registration and role-based login for users, sellers, and admins.
 *
 * @param authUseCase Provides access to authentication use cases.
 */
fun Route.authRoutes(authUseCase: AuthUseCase) {
    // Create an instance of the AuthController
    val controller = AuthController(authUseCase)

    // Group all authentication endpoints under /auth
    route("/auth") {

        // POST /auth/signUp
        // Registers a new user (no role assigned here; defaults handled internally)
        post("/signUp") {
            controller.signUp(call)
        }

        // POST /auth/login/user
        // Logs in a normal user, assigns USER role in JWT
        post("/login/user") {
            controller.login(call, UserRole.USER)
        }

        // POST /auth/login/seller
        // Logs in a seller, assigns SELLER role in JWT
        post("/login/seller") {
            controller.login(call, UserRole.SELLER)
        }

        // POST /auth/login/admin
        // Logs in an admin, assigns ADMIN role in JWT
        post("/login/admin") {
            controller.login(call, UserRole.ADMIN)
        }
    }
}
