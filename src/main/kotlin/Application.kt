package com.commerce

import com.commerce.config.configSecurity
import com.commerce.config.configSerialization
import com.commerce.config.configureDatabases
import com.commerce.config.configureRouting
import di.appModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val env = this.environment

    install(Koin) {
        slf4jLogger()
        modules(appModule(env)) // ⬅️ pass env to the module
    }
    //configureDatabases()
    configSerialization()
    configSecurity()
    configureRouting()
}
