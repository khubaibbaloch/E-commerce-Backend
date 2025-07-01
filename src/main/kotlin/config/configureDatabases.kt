package com.commerce.config

import com.commerce.data.db.repository.AuthRepository
import com.commerce.data.db.repository.CartRepository
import com.commerce.data.db.repository.OrderRepository
import com.commerce.data.db.repository.ProductRepository
import com.commerce.domain.services.AuthService
import com.commerce.domain.services.CartService
import com.commerce.domain.services.OrderService
import com.commerce.domain.services.ProductService
import data.db.repository.PaymentRepository
import domain.services.PaymentService
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

lateinit var authService: AuthService
lateinit var productService: ProductService
lateinit var cartService: CartService
lateinit var orderService: OrderService
lateinit var paymentService: PaymentService


fun Application.configureDatabases() {
    val url = environment.config.property("database.url").getString()
    val user = environment.config.property("database.user").getString()
    val password = environment.config.property("database.password").getString()
    val driver = environment.config.property("database.driver").getString()

    val database = Database.connect(url, driver, user, password)

    val repository = AuthRepository(database)
    authService = AuthService(repository)

    val productRepository = ProductRepository(database)
    productService = ProductService(productRepository)

    val cartRepository = CartRepository(database)
    cartService = CartService(cartRepository)

    val orderRepository = OrderRepository(database)
    orderService = OrderService(orderRepository)

    val paymentRepository = PaymentRepository(database)
    paymentService = PaymentService(paymentRepository,orderRepository)
}