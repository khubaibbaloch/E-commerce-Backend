package com.commerce.config

import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

// Legacy service initializations (manually created service objects)
// These were likely replaced with dependency injection (e.g., Koin) later
// Keeping them here commented can be helpful for reference or fallback testing
// lateinit var authServiceImpl: AuthServiceImpl
// lateinit var productServiceImpl: ProductServiceImpl
// lateinit var cartServiceImpl: CartServiceImpl
// lateinit var orderServiceImpl: OrderServiceImpl
// lateinit var paymentServiceImpl: PaymentServiceImpl

/**
 * Configures the connection to the relational database using Exposed ORM.
 * Reads database connection properties from `application.conf` and connects using Exposed.
 */
fun Application.configureDatabases() {
    // Read DB connection parameters from application.conf or environment
    val url = environment.config.property("database.url").getString()
    val user = environment.config.property("database.user").getString()
    val password = environment.config.property("database.password").getString()
    val driver = environment.config.property("database.driver").getString()

    // Establish a connection to the database using Exposed
    val database = Database.connect(url = url, driver = driver, user = user, password = password)

    // Below: Manual service setup (now likely replaced by Koin modules)
    // These lines show how the application was originally wiring up services without DI

    // val repository = AuthRepositoryImpl(database)
    // authServiceImpl = AuthServiceImpl(repository)

    // val productRepositoryImpl = ProductRepositoryImpl(database)
    // productServiceImpl = ProductServiceImpl(productRepositoryImpl)

    // val cartRepositoryImpl = CartRepositoryImpl(database)
    // cartServiceImpl = CartServiceImpl(cartRepositoryImpl)

    // val orderRepositoryImpl = OrderRepositoryImpl(database)
    // orderServiceImpl = OrderServiceImpl(orderRepositoryImpl)

    // val paymentRepositoryImpl = PaymentRepositoryImpl(database)
    // paymentServiceImpl = PaymentServiceImpl(paymentRepositoryImpl, orderRepositoryImpl)
}
