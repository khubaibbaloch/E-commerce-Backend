package config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

/**
 * Configures global error and status handling for the application.
 * Catches exceptions and maps specific HTTP status codes to standardized JSON responses.
 */
fun Application.configureStatusPages() {
    install(StatusPages) {

        // Handle all uncaught exceptions (default HTTP 500)
        exception<Throwable> { call, cause ->
            call.application.environment.log.error("Unhandled exception", cause) // Log the error in Ktor's logger
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to (cause.message ?: "Internal Server Error")) // Send error message to client
            )
        }

        // Respond with a standardized message for HTTP 400 Bad Request
        status(HttpStatusCode.BadRequest) { call, _ ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Bad Request"))
        }

        // Respond with a standardized message for HTTP 401 Unauthorized
        status(HttpStatusCode.Unauthorized) { call, _ ->
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized"))
        }

        // Respond with a standardized message for HTTP 403 Forbidden
        status(HttpStatusCode.Forbidden) { call, _ ->
            call.respond(HttpStatusCode.Forbidden, mapOf("error" to "Forbidden"))
        }

        // Respond with a standardized message for HTTP 404 Not Found
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Not Found"))
        }

        // Respond with a standardized message for HTTP 501 Not Implemented
        status(HttpStatusCode.NotImplemented) { call, _ ->
            call.respond(HttpStatusCode.NotImplemented, mapOf("error" to "Not Implemented"))
        }

        // Respond with a standardized message for HTTP 503 Service Unavailable
        status(HttpStatusCode.ServiceUnavailable) { call, _ ->
            call.respond(HttpStatusCode.ServiceUnavailable, mapOf("error" to "Service Unavailable"))
        }

        // Respond again with HTTP 500, in case it’s triggered explicitly by code
        status(HttpStatusCode.InternalServerError) { call, _ ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Internal Server Error"))
        }
    }
}
