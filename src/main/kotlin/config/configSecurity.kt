package com.commerce.config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configSecurity() {
    JwtConfig.init(this)

    install(Authentication) {
        jwt("auth-jwt") {
            realm = JwtConfig.realm
            verifier(JwtConfig.verifier)

            validate { credential ->
                val username = credential.payload.getClaim("userId").asString()
                if (!username.isNullOrBlank()) JWTPrincipal(credential.payload) else null
            }

            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Token is not valid or has expired"))
            }
        }
    }
}