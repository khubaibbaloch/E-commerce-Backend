package com.commerce

import com.commerce.config.configSecurity
import com.commerce.config.configSerialization
import com.commerce.config.configureDatabases
import com.commerce.config.configureRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()
    configSerialization()
    configSecurity()
    configureRouting()
}
