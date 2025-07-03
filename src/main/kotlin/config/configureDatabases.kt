package com.commerce.config

import com.commerce.data.auth.repository.AuthRepositoryImpl
import com.commerce.data.cart.repository.CartRepositoryImpl
import com.commerce.data.order.repository.OrderRepositoryImpl
import com.commerce.data.product.repository.ProductRepositoryImpl
import com.commerce.data.auth.service.AuthServiceImpl
import com.commerce.data.cart.service.CartServiceImpl
import com.commerce.data.order.service.OrderServiceImpl
import com.commerce.data.product.service.ProductServiceImpl
import com.commerce.data.payment.repository.PaymentRepositoryImpl
import com.commerce.data.payment.service.PaymentServiceImpl
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database

//lateinit var authServiceImpl: AuthServiceImpl
//lateinit var productServiceImpl: ProductServiceImpl
//lateinit var cartServiceImpl: CartServiceImpl
//lateinit var orderServiceImpl: OrderServiceImpl
//lateinit var paymentServiceImpl: PaymentServiceImpl


fun Application.configureDatabases() {
    val url = environment.config.property("database.url").getString()
    val user = environment.config.property("database.user").getString()
    val password = environment.config.property("database.password").getString()
    val driver = environment.config.property("database.driver").getString()

    val database = Database.connect(url, driver, user, password)

//    val repository = AuthRepositoryImpl(database)
//    authServiceImpl = AuthServiceImpl(repository)
//
//    val productRepositoryImpl = ProductRepositoryImpl(database)
//    productServiceImpl = ProductServiceImpl(productRepositoryImpl)
//
//    val cartRepositoryImpl = CartRepositoryImpl(database)
//    cartServiceImpl = CartServiceImpl(cartRepositoryImpl)
//
//    val orderRepositoryImpl = OrderRepositoryImpl(database)
//    orderServiceImpl = OrderServiceImpl(orderRepositoryImpl)
//
//    val paymentRepositoryImpl = PaymentRepositoryImpl(database)
//    paymentServiceImpl = PaymentServiceImpl(paymentRepositoryImpl,orderRepositoryImpl)
}