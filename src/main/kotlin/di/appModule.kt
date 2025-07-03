package di

import com.commerce.data.auth.repository.AuthRepositoryImpl
import com.commerce.data.auth.service.AuthServiceImpl
import com.commerce.data.cart.repository.CartRepositoryImpl
import com.commerce.data.cart.service.CartServiceImpl
import com.commerce.data.order.repository.OrderRepositoryImpl
import com.commerce.data.order.service.OrderServiceImpl
import com.commerce.data.payment.repository.PaymentRepositoryImpl
import com.commerce.data.payment.service.PaymentServiceImpl
import com.commerce.data.product.repository.ProductRepositoryImpl
import com.commerce.data.product.service.ProductServiceImpl
import com.commerce.domain.auth.repository.AuthRepository
import com.commerce.domain.auth.service.AuthService
import com.commerce.domain.auth.usecase.AuthUseCase
import com.commerce.domain.auth.usecase.LoginUseCase
import com.commerce.domain.auth.usecase.RegisterAndReturnUserIdUseCase
import com.commerce.domain.cart.repository.CartRepository
import com.commerce.domain.cart.service.CartService
import com.commerce.domain.cart.usecase.AddCartUseCase
import com.commerce.domain.cart.usecase.CartUseCase
import com.commerce.domain.cart.usecase.FindCartByUserIdUseCase
import com.commerce.domain.order.repository.OrderRepository
import com.commerce.domain.order.service.OrderService
import com.commerce.domain.order.usecase.OrderUseCase
import com.commerce.domain.order.usecase.PlaceOrderUseCase
import com.commerce.domain.payment.repository.PaymentRepository
import com.commerce.domain.payment.service.PaymentService
import com.commerce.domain.product.repository.ProductRepository
import com.commerce.domain.product.service.ProductService
import com.commerce.presentation.controllers.AuthController
import domain.cart.usecase.DeleteCartUseCase
import domain.cart.usecase.UpdateCartUseCase
import domain.order.usecase.CancelOrderUseCase
import domain.order.usecase.GetOrdersByUserUseCase
import domain.payment.usecase.CreatePaymentUseCase
import domain.payment.usecase.GetPaymentsUseCase
import domain.payment.usecase.PaymentUseCase
import domain.product.usecase.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.koin.dsl.module

fun appModule(env: ApplicationEnvironment) = module {

    // Database
    single<Database> {
        Database.connect(
            url = env.config.property("database.url").getString(),
            driver = env.config.property("database.driver").getString(),
            user = env.config.property("database.user").getString(),
            password = env.config.property("database.password").getString()
        )
    }

    // Auth Bindings

    // ✅ Single Instance with explicit implementation binding
    // This allows injecting both interface (AuthRepository) and class (AuthRepositoryImpl)
//    single<AuthRepositoryImpl> { AuthRepositoryImpl(get()) }
//    single<AuthRepository> { get<AuthRepositoryImpl>() }
//
//    single<AuthServiceImpl> { AuthServiceImpl(get()) }
//    single<AuthService> { get<AuthServiceImpl>() }


    // ✅ Single Instance with interface only
    // Simpler — you can only inject by interface (preferred if implementation not needed directly)
    // Comment out the ones above if using this approach

//    single<AuthRepository> { AuthRepositoryImpl(get()) }
//    single<AuthService> { AuthServiceImpl(get()) }


    // -------------------- AUTH --------------------
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }
    factory { RegisterAndReturnUserIdUseCase(get()) }
    factory { LoginUseCase(get()) }
    single { AuthUseCase(get(), get()) }


    // -------------------- PRODUCT --------------------
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<ProductService> { ProductServiceImpl(get()) }
    factory { InsertProductUseCase(get()) }
    factory { FindProductByNameUseCase(get()) }
    factory { GetAllProductsUseCase(get()) }
    factory { UpdateProductByIdUseCase(get()) }
    factory { DeleteProductByIdUseCase(get()) }
    single { ProductUseCase(get(), get(), get(), get(), get()) }


    // -------------------- CART --------------------
    single<CartRepository> { CartRepositoryImpl(get()) }
    single<CartService> { CartServiceImpl(get()) }
    factory { AddCartUseCase(get()) }
    factory { FindCartByUserIdUseCase(get()) }
    factory { DeleteCartUseCase(get()) }
    factory { UpdateCartUseCase(get()) }
    single { CartUseCase(get(), get(), get(),get()) }


    // -------------------- ORDER --------------------
    single<OrderRepository> { OrderRepositoryImpl(get()) }
    single<OrderService> { OrderServiceImpl(get()) }
    factory { CancelOrderUseCase(get()) }
    factory { GetOrdersByUserUseCase(get()) }
    factory { PlaceOrderUseCase(get()) }
    single { OrderUseCase(get(), get(),get()) }


    // -------------------- PAYMENT --------------------
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }
    single<PaymentService> { PaymentServiceImpl(get(),get()) }
    factory { CreatePaymentUseCase(get()) }
    factory { GetPaymentsUseCase(get()) }
    single { PaymentUseCase(get(), get()) }



}
