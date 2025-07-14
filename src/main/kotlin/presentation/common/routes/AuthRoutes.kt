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
         * Handles user registration using email and password.
         */
        post("/signUp") {
            controller.signUp(call)
        }

        /**
         * POST /auth/login/user
         * Authenticates a regular user and returns a JWT with USER role.
         */
        post("/login/user") {
            controller.login(call, UserRole.USER)
        }

        /**
         * POST /auth/login/seller
         * Authenticates a seller and returns a JWT with SELLER role.
         */
        post("/login/seller") {
            controller.login(call, UserRole.SELLER)
        }

        /**
         * POST /auth/login/admin
         * Authenticates an admin and returns a JWT with ADMIN role.
         */
        post("/login/admin") {
            controller.login(call, UserRole.ADMIN)
        }

        /**
         * GET /auth/emailVerification
         * Verifies user email through token in the query string.
         * Triggered via verification email link.
         */
        get("/emailVerification") {
            controller.verifyEmailWithLink(call)
        }

        /**
         * POST /auth/emailVerification
         * (Currently commented out) Would verify email using OTP.
         */
//        post("/emailVerification") {
//            controller.verifyEmailWithOtp(call)
//        }

        // ------------------------ Google OAuth ------------------------

        /**
         * GET /auth/oauth/google
         * Initiates Google OAuth login via redirection.
         * Handled by the Ktor OAuth plugin.
         */
        authenticate("google-oauth") {
            get("/oauth/google") {
                // Redirection handled by Ktor OAuth plugin
            }

            /**
             * GET /auth/oauth/google/callback
             * Handles Google's callback with authorization code.
             * Exchanges token and fetches Google user profile.
             */
            get("/oauth/google/callback") {
                controller.googleSignUp(call)
            }
        }
    }
}
