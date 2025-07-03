package com.commerce.presentation.routes

import com.commerce.data.auth.service.AuthServiceImpl
import com.commerce.domain.auth.usecase.AuthUseCase
import com.commerce.presentation.controllers.AuthController
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Route.authRoutes(authUseCase: AuthUseCase) {
    val controller = AuthController(authUseCase)

    route("/auth") {
        post("/signUp") { controller.signUp(call) }
        post("/login") { controller.login(call) }
    }
}

