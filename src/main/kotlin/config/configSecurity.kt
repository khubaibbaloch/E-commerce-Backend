package com.commerce.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import utils.HttpClientHelper

/**
 * Configures the security layer of the application.
 * Includes:
 * ✅ JWT-based Authentication (for protected API access)
 * ✅ Google OAuth2 Authentication (for social login)
 */
fun Application.configSecurity() {
    // 🔐 Initialize JWT configuration (loads secret, issuer, etc.)
    JwtConfig.init(this)

    // 🔐 Install Ktor's Authentication plugin
    install(Authentication) {

        // -------------------- JWT AUTH --------------------
        jwt("auth-jwt") {
            // Used in the WWW-Authenticate header
            realm = JwtConfig.realm

            // Set verifier to check the token signature and expiration
            verifier(JwtConfig.verifier)

            /**
             * Validate JWT claims.
             * Allow only if both `userId` and `role` claims are present and not blank.
             */
            validate { credential ->
                val userId = credential.payload.getClaim("userId").asString()
                val role = credential.payload.getClaim("role").asString()

                if (!userId.isNullOrBlank() && !role.isNullOrBlank()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }

            /**
             * Return 401 Unauthorized if JWT is invalid or missing.
             */
            challenge { _, _ ->
                call.respond(
                    HttpStatusCode.Unauthorized,
                    mapOf("error" to "Token is not valid or has expired")
                )
            }
        }

        // -------------------- GOOGLE OAUTH AUTH --------------------
        /**
         * ✅ Google OAuth 2.0 Authentication
         * Used for signing in with Google accounts.
         * After successful login, it redirects to /auth/oauth/google/callback
         */
        oauth("google-oauth") {
            urlProvider = { "http://localhost:8080/auth/oauth/google/callback" }

            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                    accessTokenUrl = "https://oauth2.googleapis.com/token",
                    requestMethod = HttpMethod.Post,
                    clientId = "1046768830009-r4mvkipmeg109c9lah9ptjh1c696re1s.apps.googleusercontent.com",
                    clientSecret = "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",
                    defaultScopes = listOf("profile", "email")
                )
            }

            client = HttpClientHelper.client
        }
    }
}
