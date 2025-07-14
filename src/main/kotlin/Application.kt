package com.commerce

import com.commerce.config.*
import config.configureStatusPages
import di.appModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    // Entry point for running Ktor with Netty engine
    io.ktor.server.netty.EngineMain.main(args)
}

/**
 * Main application module function invoked when the server starts.
 * This is where all application-wide features and configurations are initialized.
 */
fun Application.module() {

    val env = this.environment // Get application environment (dev, prod, etc.)

    // 🔧 Set up Koin for Dependency Injection
    install(Koin) {
        slf4jLogger() // Enable Koin logging with SLF4J
        modules(appModule(env)) // Load your Koin DI module
    }

    // 🔐 Serialization (Kotlinx or other) for JSON/Content Negotiation
    configSerialization()

    // 📊 Optional: Monitoring (e.g., call logging, metrics)
    configureMonitoring()

    // 🛑 Exception Handling and Status Code Customization
   // configureStatusPages()

    // 🔒 JWT Authentication and Role-Based Security
    configSecurity()

    // 🌐 Define All Routing for HTTP Endpoints
    configureRouting()
}
