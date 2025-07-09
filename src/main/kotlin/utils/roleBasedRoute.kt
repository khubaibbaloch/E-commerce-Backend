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


fun Route.roleBasedRoute(vararg roles: UserRole, build: Route.() -> Unit): Route {
    install(RoleBasedAccessPlugin) {
        allowedRoles = roles.map { it.name }.toSet()
    }
    build()
    return this
}
