package com.commerce.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

/**
 * Configures JWT-based authentication for the application.
 * This function sets up the "auth-jwt" provider, which is used across the app for protected routes.
 */
fun Application.configSecurity() {
    // Initialize JWT configuration (loads secret, issuer, etc.)
    JwtConfig.init(this)

    // Install Ktor's Authentication plugin with JWT provider
    install(Authentication) {
        jwt("auth-jwt") {
            // Realm is used in the WWW-Authenticate header (typically browser-level)
            realm = JwtConfig.realm

            // Set the JWT verifier used to validate incoming tokens
            verifier(JwtConfig.verifier)

            /**
             * Validate incoming JWT credentials.
             * Only allow requests where both `userId` and `role` claims exist and are non-blank.
             */
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()
                val role = credential.payload.getClaim("role").asString()

                if (!userId.isNullOrBlank() && !role.isNullOrBlank()) {
                    // If both userId and role are valid, proceed with the request
                    JWTPrincipal(credential.payload)
                } else {
                    // Otherwise, authentication fails
                    null
                }
            }

            /**
             * Handle cases where JWT is missing, invalid, or expired.
             * Responds with HTTP 401 Unauthorized and a custom error message.
             */
            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    mapOf("error" to "Token is not valid or has expired")
                )
            }
        }
    }
}
