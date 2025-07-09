package domain.user.cart.usecase

import com.commerce.domain.cart.model.CartUpdateEntity
import com.commerce.domain.cart.service.CartService

class UpdateCartUseCase(private val cartService: CartService) {
    suspend operator fun invoke (cartId: String, updateEntity: CartUpdateEntity): Boolean{
        return cartService.updateCart(cartId, updateEntity)
    }
}