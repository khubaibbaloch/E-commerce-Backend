package com.commerce.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import domain.common.auth.model.UserRole
import io.ktor.server.application.*
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal

/**
 * Object responsible for configuring and generating JWT tokens, as well as verifying and extracting claims.
 */
object JwtConfig {

    // Configuration values loaded from application.conf
    private lateinit var secret: String
    private lateinit var issuer: String
    private lateinit var audience: String
    lateinit var realm: String
    private lateinit var algorithm: Algorithm

    /**
     * Initializes JWT settings using application environment properties.
     * Called from configSecurity() during application setup.
     */
    fun init(app: Application) {
        secret = app.environment.config.property("jwt.secret").getString()
        issuer = app.environment.config.property("jwt.issuer").getString()
        audience = app.environment.config.property("jwt.audience").getString()
        realm = app.environment.config.property("jwt.realm").getString()
        algorithm = Algorithm.HMAC256(secret)
    }

    /**
     * Returns a verifier configured with the application's JWT algorithm, issuer, and audience.
     * Used by Ktor's JWT authentication plugin.
     */
    val verifier: JWTVerifier
        get() = JWT.require(algorithm)
            .withIssuer(issuer)
            .withAudience(audience)
            .build()

    /**
     * Generates a signed JWT token containing userId and role claims.
     * Can be extended to include expiration or other metadata.
     */
    fun generateToken(userId: String, role: UserRole): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("userId", userId)
        .withClaim("role", role.name)
        //.withExpiresAt(Date(System.currentTimeMillis() + 5000)) // Optional expiry
        .sign(algorithm)

    /**
     * Extracts the 'role' claim from the JWT token associated with the current request.
     * Returns null if not present.
     */
    fun getRoleFromToken(call: ApplicationCall): String? =
        call.principal<JWTPrincipal>("auth-jwt")
            ?.payload
            ?.getClaim("role")
            ?.asString()
}
