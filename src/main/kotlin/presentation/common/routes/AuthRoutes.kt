package com.commerce.presentation.common.routes

import com.commerce.domain.auth.usecase.AuthUseCase
import com.commerce.presentation.common.controllers.AuthController
import domain.common.auth.model.UserRole
import io.ktor.server.routing.*


fun Route.authRoutes(authUseCase: AuthUseCase) {
    val controller = AuthController(authUseCase)

    route("/auth") {
        post("/signUp") { controller.signUp(call) }

        // separate login routes by role
        post("/login/user") { controller.login(call, UserRole.USER) }
        post("/login/seller") { controller.login(call, UserRole.SELLER) }
        post("/login/admin") { controller.login(call, UserRole.ADMIN) }
    }
}

