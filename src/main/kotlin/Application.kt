package com.commerce

import com.commerce.config.*
import config.configureStatusPages
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
        modules(appModule(env))
    }
    //configureDatabases()
    configSerialization()
    configureMonitoring()
    configureStatusPages()
    configSecurity()
    configureRouting()
}
