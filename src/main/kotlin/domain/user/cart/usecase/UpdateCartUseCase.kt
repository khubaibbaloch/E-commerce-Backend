package domain.user.cart.usecase

import com.commerce.domain.user.cart.model.CartUpdateEntity
import com.commerce.domain.user.cart.service.CartService

/**
 * Use case for updating the quantity of a specific cart item.
 *
 * This class delegates the update logic to the [CartService], which handles
 * business rules and validation for cart operations.
 *
 * @param cartService Service interface providing cart-related business operations.
 */
class UpdateCartUseCase(private val cartService: CartService) {

    /**
     * Invokes the use case to update the quantity of a cart item.
     *
     * @param cartId The ID of the cart item to be updated.
     * @param updateEntity Object containing the new quantity value.
     * @return True if the update was successful, false otherwise.
     */
    suspend operator fun invoke(cartId: String, updateEntity: CartUpdateEntity): Boolean {
        return cartService.updateCart(cartId, updateEntity)
    }
}
