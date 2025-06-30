package com.commerce.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.JWTVerifier
import io.ktor.server.application.*
import java.util.*

object JwtConfig {
    private lateinit var secret: String
    private lateinit var issuer: String
    private lateinit var audience: String
    lateinit var realm: String
    private lateinit var algorithm: Algorithm

    fun init(app: Application) {
        secret = app.environment.config.property("jwt.secret").getString()
        issuer = app.environment.config.property("jwt.issuer").getString()
        audience = app.environment.config.property("jwt.audience").getString()
        realm = app.environment.config.property("jwt.realm").getString()
        algorithm = Algorithm.HMAC256(secret)
    }

    val verifier: JWTVerifier
        get() = JWT.require(algorithm)
            .withIssuer(issuer)
            .withAudience(audience)
            .build()

    fun generateToken(userId: String): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("userId", userId)
        //.withExpiresAt(Date(System.currentTimeMillis() + 5000))
        .sign(algorithm)
}
