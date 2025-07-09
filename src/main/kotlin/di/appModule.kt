package di

import com.commerce.data.admin.repository.AdminRepositoryImpl
import com.commerce.data.admin.service.AdminServiceImpl
import com.commerce.data.common.auth.repository.AuthRepositoryImpl
import com.commerce.data.common.auth.service.AuthServiceImpl
import com.commerce.data.cart.repository.CartRepositoryImpl
import com.commerce.data.cart.service.CartServiceImpl
import com.commerce.data.order.repository.OrderRepositoryImpl
import com.commerce.data.order.service.OrderServiceImpl
import com.commerce.data.payment.repository.PaymentRepositoryImpl
import com.commerce.data.payment.service.PaymentServiceImpl
import com.commerce.data.product.repository.UserProductRepositoryImpl
import com.commerce.data.product.service.UserProductServiceImpl
import com.commerce.data.seller.repository.SellerProductRepositoryImpl
import com.commerce.data.seller.service.SellerProductServiceImpl
import com.commerce.domain.admin.repository.AdminRepository
import com.commerce.domain.admin.service.AdminService
import com.commerce.domain.admin.usecase.AdminUseCase
import com.commerce.domain.admin.usecase.GetAllUserUseCase
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

    // -------------------- COMMON--------------------

    // -------------------- AUTH --------------------
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<AuthService> { AuthServiceImpl(get()) }
    factory { RegisterAndReturnUserIdUseCase(get()) }
    factory { LoginUseCase(get()) }
    single { AuthUseCase(get(), get()) }


    // -------------------- USER --------------------

    // -------------------- PRODUCT --------------------
    single<UserProductRepository> { UserProductRepositoryImpl(get()) }
    single<UserProductService> { UserProductServiceImpl(get()) }
    factory { FindProductByNameUseCase(get()) }
    factory { GetAllProductsUseCase(get()) }
    factory { InsertProductUseCase(get()) }
    factory { UpdateProductByIdUseCase(get()) }
    factory { DeleteProductByIdUseCase(get()) }
    single { UserProductUseCase(get(), get()) }


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


    // -------------------- ADMIN --------------------
    single<AdminRepository> { AdminRepositoryImpl(get()) }
    single<AdminService> { AdminServiceImpl(get()) }
    factory { GetAllUserUseCase(get()) }
    factory { GetAllProductsUseCase(get()) }
    single { AdminUseCase(get(),get()) }


    // -------------------- SELLER --------------------

    single<SellerProductRepository> { SellerProductRepositoryImpl(get()) }
    single<SellerProductService> { SellerProductServiceImpl(get()) }
    factory { InsertProductUseCase(get()) }
    factory { UpdateProductByIdUseCase(get()) }
    factory { DeleteProductByIdUseCase(get()) }
    single { SellerProductUseCase(get(), get(), get()) }


}
