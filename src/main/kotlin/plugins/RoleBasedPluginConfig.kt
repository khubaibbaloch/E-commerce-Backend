// RoleBasedAuthorization.kt
package plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

class RoleBasedPluginConfig {
    var allowedRoles: Set<String> = emptySet()
}

val RoleBasedAccessPlugin = createRouteScopedPlugin(
    name = "RoleBasedAccessPlugin",
    createConfiguration = ::RoleBasedPluginConfig
) {
    val roles = pluginConfig.allowedRoles

    on(AuthenticationChecked) { call ->
        val tokenRole = call.principal<JWTPrincipal>()?.payload?.getClaim("role")?.asString()
        println("✅ JWT Role from token: $tokenRole, Allowed: $roles")

        if (tokenRole == null || tokenRole !in roles) {
            println("❌ Access denied for role: $tokenRole")
            call.respond(HttpStatusCode.Forbidden, "Access denied for role: $tokenRole")
        }
    }
}
