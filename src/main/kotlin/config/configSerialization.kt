package com.commerce.config

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

/**
 * Configures JSON serialization for the Ktor application using kotlinx.serialization.
 * This ensures all incoming and outgoing content is properly serialized and deserialized as JSON.
 */
fun Application.configSerialization() {
    // Install the ContentNegotiation plugin, which allows automatic serialization of responses and requests
    install(ContentNegotiation) {
        // Use kotlinx.serialization JSON engine with custom settings
        json(Json {
            prettyPrint = true   // Formats the JSON output for readability (useful during development)
            isLenient = true     // Allows parsing of non-strict JSON (e.g., missing quotes or unquoted keys)
        })
    }
}
