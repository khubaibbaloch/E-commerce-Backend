package com.commerce.presentation.common.routes

import com.commerce.domain.auth.usecase.AuthUseCase
import com.commerce.presentation.common.controllers.AuthController
import io.ktor.server.routing.*


fun Route.authRoutes(authUseCase: AuthUseCase) {
    val controller = AuthController(authUseCase)

    route("/auth") {
        post("/signUp") { controller.signUp(call) }
        post("/login") { controller.login(call) }
    }
}

