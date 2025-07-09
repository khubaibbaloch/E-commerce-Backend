package domain.user.cart.usecase

import com.commerce.domain.cart.service.CartService

class DeleteCartUseCase(private val cartService: CartService) {
    suspend operator fun invoke (cartId: String): Boolean{
        return cartService.deleteCart(cartId)
    }
}