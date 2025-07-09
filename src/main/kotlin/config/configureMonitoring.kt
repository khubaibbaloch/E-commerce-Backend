package com.commerce.config

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import io.ktor.http.*
import io.ktor.server.request.*
import org.slf4j.event.Level

/**
 * Enables request logging for all incoming HTTP calls in the application.
 * Uses SLF4J (Simple Logging Facade for Java) to log essential request metadata.
 *
 * Logs will include:
 * - HTTP method and URL
 * - Status code
 * - Duration and processing info
 */
fun Application.configureMonitoring() {
    install(CallLogging) {
        // Log at INFO level (you can change to DEBUG for more verbose output)
        level = Level.INFO

        // Only log calls with a path starting with "/", which includes all standard routes
        filter { call -> call.request.path().startsWith("/") }

        // Optionally, you can customize the format or include additional call info
        // format { call -> "HTTP ${call.request.httpMethod.value} - ${call.request.uri}" }
    }
}
