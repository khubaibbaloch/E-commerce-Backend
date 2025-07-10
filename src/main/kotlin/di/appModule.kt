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
import com.commerce.domain.common.auth.usecase.LoginUseCase
import com.commerce.domain.common.auth.usecase.RegisterAndReturnUserIdUseCase
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
import com.commerce.domain.seller.repository.SellerProductRepository
import com.commerce.domain.seller.service.SellerProductService
import com.commerce.domain.seller.usecase.SellerProductUseCase
import com.commerce.domain.seller.usecase.UpdateProductByIdUseCase
import com.commerce.domain.user.product.repository.UserProductRepository
import com.commerce.domain.user.product.service.UserProductService
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

    // -------------------- DATABASE --------------------
    // Bind a singleton instance of the Exposed SQL database using environment config
    single<Database> {
        Database.connect(
            url = env.config.property("database.url").getString(),
            driver = env.config.property("database.driver").getString(),
            user = env.config.property("database.user").getString(),
            password = env.config.property("database.password").getString()
        )
    }

    // -------------------- AUTH --------------------
    // Auth Repository and Service
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }

    // Auth Use Cases
    factory { RegisterAndReturnUserIdUseCase(get()) }
    factory { LoginUseCase(get()) }

    // Grouped Auth UseCase (container)
    single { AuthUseCase(get(), get()) }

    // -------------------- USER: PRODUCT --------------------
    // Product search and retrieval for users
    single<UserProductRepository> { UserProductRepositoryImpl(get()) }
    single<UserProductService> { UserProductServiceImpl(get()) }

    // User Product Use Cases
    factory { FindProductByNameUseCase(get()) }
    factory { GetAllProductsUseCase(get()) }
    factory { InsertProductUseCase(get()) }              // (Optional use for seller/admin)
    factory { UpdateProductByIdUseCase(get()) }          // (Optional use for seller/admin)
    factory { DeleteProductByIdUseCase(get()) }          // (Optional use for seller/admin)

    // Grouped User Product UseCase
    single { UserProductUseCase(get(), get()) }

    // -------------------- USER: CART --------------------
    single<CartRepository> { CartRepositoryImpl(get()) }
    single<CartService> { CartServiceImpl(get()) }

    // Cart Use Cases
    factory { AddCartUseCase(get()) }
    factory { FindCartByUserIdUseCase(get()) }
    factory { DeleteCartUseCase(get()) }
    factory { UpdateCartUseCase(get()) }

    // Grouped Cart UseCase
    single { CartUseCase(get(), get(), get(), get()) }

    // -------------------- USER: ORDER --------------------
    single<OrderRepository> { OrderRepositoryImpl(get()) }
    single<OrderService> { OrderServiceImpl(get()) }

    // Order Use Cases
    factory { CancelOrderUseCase(get()) }
    factory { GetOrdersByUserUseCase(get()) }
    factory { PlaceOrderUseCase(get()) }

    // Grouped Order UseCase
    single { OrderUseCase(get(), get(), get()) }

    // -------------------- USER: PAYMENT --------------------
    single<PaymentRepository> { PaymentRepositoryImpl(get()) }
    single<PaymentService> { PaymentServiceImpl(get(), get()) }

    // Payment Use Cases
    factory { CreatePaymentUseCase(get()) }
    factory { GetPaymentsUseCase(get()) }

    // Grouped Payment UseCase
    single { PaymentUseCase(get(), get()) }

    // -------------------- ADMIN --------------------
    single<AdminRepository> { AdminRepositoryImpl(get()) }
    single<AdminService> { AdminServiceImpl(get()) }

    // Admin Use Cases
    factory { GetAllUserUseCase(get()) }
    factory { GetAllProductsUseCase(get()) }

    // Grouped Admin UseCase
    single { AdminUseCase(get(), get()) }

    // -------------------- SELLER --------------------
    single<SellerProductRepository> { SellerProductRepositoryImpl(get()) }
    single<SellerProductService> { SellerProductServiceImpl(get()) }

    // Seller Product Use Cases
    factory { InsertProductUseCase(get()) }
    factory { UpdateProductByIdUseCase(get()) }
    factory { DeleteProductByIdUseCase(get()) }

    // Grouped Seller Product UseCase
    single { SellerProductUseCase(get(), get(), get()) }
}