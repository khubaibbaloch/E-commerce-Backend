package com.commerce.utils

import com.commerce.config.JwtConfig
import com.commerce.config.JwtConfig.getRoleFromToken
import domain.common.auth.model.UserRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.cancel
import plugins.RoleBasedAccessPlugin

/**
 * A utility function to simplify defining role-restricted routes.
 *
 * @param roles A vararg of allowed user roles (e.g., ADMIN, USER, SELLER).
 * @param build A lambda that defines the routing logic to apply if role check passes.
 *
 * This function:
 * - Installs the RoleBasedAccessPlugin with allowed roles.
 * - Executes the provided route block only if the user's JWT role matches.
 *
 * Usage Example:
 * ```kotlin
 * route("/admin") {
 *     roleBasedRoute(UserRole.ADMIN) {
 *         get("/dashboard") { ... }
 *     }
 * }
 * ```
 */
fun Route.roleBasedRoute(vararg roles: UserRole, build: Route.() -> Unit): Route {
    // Attach custom plugin that validates the user role from JWT token
    install(RoleBasedAccessPlugin) {
        // Convert the UserRole enums to a set of role names like "ADMIN", "USER", etc.
        allowedRoles = roles.map { it.name }.toSet()
    }

    // Apply the provided routing logic within this protected route
    build()

    // Return the route for further chaining if needed
    return this
}
