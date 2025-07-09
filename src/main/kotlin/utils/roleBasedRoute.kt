package com.commerce.utils


import domain.common.auth.model.UserRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.cancel

fun Route.roleBasedRoute(expectedRole: UserRole, build: Route.() -> Unit): Route {
    val plugin = createRouteScopedPlugin(name = "RoleBasedAccessPlugin-${expectedRole.name}") {
        onCall { call ->
            val principal = call.principal<JWTPrincipal>("auth-jwt")
            val role = principal?.payload?.getClaim("role")?.asString()

            println("JWT role from token: '$role', Expected role: '${expectedRole.name}'")

            if (role != expectedRole.name) {
                println("Access denied for role: $role vs expected ${expectedRole.name}")
                call.respond(HttpStatusCode.Forbidden, "Access denied for role: $role")
                return@onCall
            }
        }

    }
    this.install(plugin)
    return this.apply(build)
}
