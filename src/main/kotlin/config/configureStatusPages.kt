package config

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun Application.configureStatusPages() {
    install(StatusPages) {
        // Handle all uncaught exceptions (default 500)
        exception<Throwable> { call, cause ->
            call.application.environment.log.error("Unhandled exception", cause)
            call.respond(
                HttpStatusCode.InternalServerError,
                mapOf("error" to (cause.message ?: "Internal Server Error"))
            )
        }

        // Built-in HTTP Status Code Handlers
        status(HttpStatusCode.BadRequest) { call, _ ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to "Bad Request"))
        }

        status(HttpStatusCode.Unauthorized) { call, _ ->
            call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Unauthorized"))
        }

        status(HttpStatusCode.Forbidden) { call, _ ->
            call.respond(HttpStatusCode.Forbidden, mapOf("error" to "Forbidden"))
        }

        status(HttpStatusCode.NotFound) { call, _ ->
            call.respond(HttpStatusCode.NotFound, mapOf("error" to "Not Found"))
        }

        status(HttpStatusCode.NotImplemented) { call, _ ->
            call.respond(HttpStatusCode.NotImplemented, mapOf("error" to "Not Implemented"))
        }

        status(HttpStatusCode.ServiceUnavailable) { call, _ ->
            call.respond(HttpStatusCode.ServiceUnavailable, mapOf("error" to "Service Unavailable"))
        }

        status(HttpStatusCode.InternalServerError) { call, _ ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "Internal Server Error"))
        }
    }
}
