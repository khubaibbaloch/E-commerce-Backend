package com.commerce.presentation.common.routes

import com.commerce.domain.common.auth.usecase.AuthUseCase
import com.commerce.presentation.common.controllers.AuthController
import domain.common.auth.model.UserRole
import io.ktor.server.auth.authenticate
import io.ktor.server.routing.*

/**
 * Defines all authentication-related routes under the `/auth` prefix.
 * Supports traditional login/signup and Google OAuth-based login.
 *
 * @param authUseCase Provides access to authentication use cases (register/login logic).
 */
fun Route.authRoutes(authUseCase: AuthUseCase) {
    // Inject controller for handling authentication logic
    val controller = AuthController(authUseCase)

    route("/auth") {

        // ------------------------ Traditional Auth ------------------------

        /**
         * POST /auth/signUp
         * Handles user registration using email/password.
         */
        post("/signUp") {
            controller.signUp(call)
        }

        /**
         * POST /auth/login/user
         * Authenticates a user and returns JWT with USER role.
         */
        post("/login/user") {
            controller.login(call, UserRole.USER)
        }

        /**
         * POST /auth/login/seller
         * Authenticates a seller and returns JWT with SELLER role.
         */
        post("/login/seller") {
            controller.login(call, UserRole.SELLER)
        }

        /**
         * POST /auth/login/admin
         * Authenticates an admin and returns JWT with ADMIN role.
         */
        post("/login/admin") {
            controller.login(call, UserRole.ADMIN)
        }

        // ------------------------ Google OAuth ------------------------

        /**
         * GET /auth/oauth/google
         * Initiates Google OAuth login by redirecting to Google.
         */
        authenticate("google-oauth") {
            get("/oauth/google") {
                // Redirection handled by Ktor OAuth plugin
            }

            /**
             * GET /auth/oauth/google/callback
             * Handles callback from Google after user consents.
             * Delegates token exchange & user info fetching to controller.
             */
            get("/oauth/google/callback") {
                controller.googleSignUp(call)
            }
        }
    }
}
