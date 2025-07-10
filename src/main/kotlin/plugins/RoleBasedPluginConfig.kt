package plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

/**
 * Configuration class for the RoleBasedAccessPlugin.
 * Holds the set of roles allowed to access a particular route.
 */
class RoleBasedPluginConfig {
    var allowedRoles: Set<String> = emptySet()
}

/**
 * A Ktor route-scoped plugin that enforces role-based access control.
 *
 * This plugin checks the "role" claim in the JWT token and compares it
 * against a list of allowed roles defined in [RoleBasedPluginConfig].
 * If the role is not allowed, it responds with HTTP 403 Forbidden.
 */
val RoleBasedAccessPlugin = createRouteScopedPlugin(
    name = "RoleBasedAccessPlugin",
    createConfiguration = ::RoleBasedPluginConfig
) {
    // Retrieve allowed roles from the plugin config
    val roles = pluginConfig.allowedRoles

    // Hook that runs after authentication is checked
    on(AuthenticationChecked) { call ->
        // Extract the "role" claim from the JWT token
        val tokenRole = call.principal<JWTPrincipal>()?.payload?.getClaim("role")?.asString()
        println("✅ JWT Role from token: $tokenRole, Allowed: $roles")

        // If role is missing or not allowed, respond with 403 Forbidden
        if (tokenRole == null || tokenRole !in roles) {
            println("❌ Access denied for role: $tokenRole")
            call.respond(HttpStatusCode.Forbidden, "Access denied for role: $tokenRole")
        }
    }
}
