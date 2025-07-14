package di

import com.commerce.data.admin.repository.AdminRepositoryImpl
import com.commerce.data.admin.service.AdminServiceImpl
import com.commerce.data.common.auth.repository.AuthRepositoryImpl
import com.commerce.data.common.auth.service.AuthServiceImpl
import com.commerce.data.user.cart.repository.CartRepositoryImpl
import com.commerce.data.user.cart.service.CartServiceImpl
import com.commerce.data.user.order.repository.OrderRepositoryImpl
import com.commerce.data.user.order.service.OrderServiceImpl
import com.commerce.data.user.payment.repository.PaymentRepositoryImpl
import com.commerce.data.user.payment.service.PaymentServiceImpl
import com.commerce.data.user.product.repository.UserProductRepositoryImpl
import com.commerce.data.user.product.service.UserProductServiceImpl
import com.commerce.data.seller.repository.SellerProductRepositoryImpl
import com.commerce.data.seller.service.SellerProductServiceImpl
import com.commerce.domain.admin.repository.AdminRepository
import com.commerce.domain.admin.service.AdminService
import com.commerce.domain.admin.usecase.AdminUseCase
import com.commerce.domain.admin.usecase.GetAllUserUseCase
import com.commerce.domain.common.auth.repository.AuthRepository
import com.commerce.domain.common.auth.service.AuthService
import com.commerce.domain.common.auth.usecase.AuthUseCase
import com.commerce.domain.common.auth.usecase.GetEmailVerificationUseCase
import com.commerce.domain.common.auth.usecase.LoginUseCase
import com.commerce.domain.common.auth.usecase.RegisterAndReturnUserIdUseCase
import com.commerce.domain.user.cart.repository.CartRepository
import com.commerce.domain.user.cart.service.CartService
import com.commerce.domain.user.cart.usecase.AddCartUseCase
import com.commerce.domain.user.cart.usecase.CartUseCase
import com.commerce.domain.user.cart.usecase.FindCartByUserIdUseCase
import com.commerce.domain.user.order.repository.OrderRepository
import com.commerce.domain.user.order.service.OrderService
import com.commerce.domain.user.order.usecase.OrderUseCase
import com.commerce.domain.user.order.usecase.PlaceOrderUseCase
import com.commerce.domain.user.payment.repository.PaymentRepository
import com.commerce.domain.user.payment.service.PaymentService
import com.commerce.domain.seller.repository.SellerProductRepository
import com.commerce.domain.seller.service.SellerProductService
import com.commerce.domain.seller.usecase.DeleteProductByIdUseCase
import com.commerce.domain.seller.usecase.InsertProductUseCase
import com.commerce.domain.seller.usecase.SellerProductUseCase
import com.commerce.domain.seller.usecase.UpdateProductByIdUseCase
import com.commerce.domain.user.product.repository.UserProductRepository
import com.commerce.domain.user.product.service.UserProductService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import domain.common.auth.usecase.MarkEmailAsVerifiedUseCase
import domain.common.auth.usecase.UpsertEmailVerificationUseCase
import domain.user.cart.usecase.DeleteCartUseCase
import domain.user.cart.usecase.UpdateCartUseCase
import domain.user.order.usecase.CancelOrderUseCase
import domain.user.order.usecase.GetOrdersByUserUseCase
import domain.user.payment.usecase.CreatePaymentUseCase
import domain.user.payment.usecase.GetPaymentsUseCase
import domain.user.payment.usecase.PaymentUseCase
import domain.user.product.usecase.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.koin.dsl.module

/**
 * Koin Dependency Injection module for the entire application.
 * Binds interfaces to implementations and wires use cases and services.
 */
fun appModule(env: ApplicationEnvironment) = module {

    // -------------------- DATABASE CONFIG --------------------

    /**
     * 🗃️ Previous local file-based (or H2) database config (Deprecated)
     * Commented out: using hardcoded config values from application.conf
     */
//    single<Database> {
//        Database.connect(
//            url = env.config.property("database.url").getString(),
//            driver = env.config.property("database.driver").getString(),
//            user = env.config.property("database.user").getString(),
//            password = env.config.property("database.password").getString()
//        )
//    }

    /**
     * 🐘 PostgreSQL Configuration using HikariCP (connection pooling)
     * Ensures efficient DB connections for production usage
     */
    single<Database> {
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/commerce" // 🛠️ DB name
            driverClassName = "org.postgresql.Driver"
            username = "postgres"                                 // 🔐 Your DB username
            password = "Khubaib@301030"                            // 🔐 Your DB password
            maximumPoolSize = 5                                    // Max simultaneous DB connections
            isAutoCommit = false                                   // Manual transaction control
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"  // Prevents dirty reads
        }
        val dataSource = HikariDataSource(config)
        Database.connect(dataSource)
    }

    // -------------------- AUTH --------------------
    // Repositories & Services
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }

    // Use Cases (individual responsibilities)
    factory { RegisterAndReturnUserIdUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { GetEmailVerificationUseCase(get()) }
    factory { UpsertEmailVerificationUseCase(get()) }
    factory { MarkEmailAsVerifiedUseCase(get()) } // ✅ Added: Marks email as verified after OTP

    // Use Case Group for Auth domain
    single {
        AuthUseCase(
            get(), // Register
            get(), // Login
            get(), // Get Verification
            get(), // Upsert Verification
            get()  // ✅ Added: Mark Email Verified
        )
    }

    // -------------------- USER: PRODUCT --------------------
    // Repositories & Services
    single<UserProductRepository> { UserProductRepositoryImpl(get()) }
    single<UserProductService> { UserProductServiceImpl(get()) }

    // Use Cases
    factory { FindProductByNameUseCase(get()) }
    factory { GetAllProductsUseCase(get()) }
    factory { InsertProductUseCase(get()) }              // Also used by Seller/Admin
    factory { UpdateProductByIdUseCase(get()) }
    factory { DeleteProductByIdUseCase(get()) }

    // Use Case Group for user product browsing
    single { UserProductUseCase(get(), get()) }

    // -------------------- USER: CART --------------------
    single<CartRepository> { CartRepositoryImpl(get()) }
    single<CartService> { CartServiceImpl(get()) }

    factory { AddCartUseCase(get()) }
    factory { FindCartByUserIdUseCase(get()) }
    factory { DeleteCartUseCase(get()) }
    factory { UpdateCartUseCase(get()) }

    // Use Case Group for managing user cart
    single { CartUseCase(get(), get(), get(), get()) }

    // -------------------- USER: ORDER --------------------
    single<OrderRepository> { OrderRepositoryImpl(get()) }
    single<OrderService> { OrderServiceImpl(get()) }

    factory { CancelOrderUseCase(get()) }
    factory { GetOrdersByUserUseCase(get()) }
    factory { PlaceOrderUseCase(get()) }

    // Use Case Group for order management
    single { OrderUseCase(get(), get(), get()) }

    // -------------------- USER: PAYMENT --------------------
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }
    single<PaymentService> { PaymentServiceImpl(get(), get()) }

    factory { CreatePaymentUseCase(get()) }
    factory { GetPaymentsUseCase(get()) }

    // Use Case Group for handling payments
    single { PaymentUseCase(get(), get()) }

    // -------------------- ADMIN --------------------
    single<AdminRepository> { AdminRepositoryImpl(get()) }
    single<AdminService> { AdminServiceImpl(get()) }

    factory { GetAllUserUseCase(get()) }
    factory { GetAllProductsUseCase(get()) } // ✅ Added for admin product access

    // Admin Use Case Group
    single { AdminUseCase(get(), get()) }

    // -------------------- SELLER --------------------
    single<SellerProductRepository> { SellerProductRepositoryImpl(get()) }
    single<SellerProductService> { SellerProductServiceImpl(get()) }

    factory { InsertProductUseCase(get()) }
    factory { UpdateProductByIdUseCase(get()) }
    factory { DeleteProductByIdUseCase(get()) }

    // Seller Use Case Group
    single { SellerProductUseCase(get(), get(), get()) }
}