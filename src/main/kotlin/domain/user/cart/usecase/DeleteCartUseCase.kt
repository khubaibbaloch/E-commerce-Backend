package domain.user.cart.usecase

import com.commerce.domain.user.cart.service.CartService

/**
 * Use case responsible for deleting a cart item based on its cart ID.
 *
 * Delegates the operation to the CartService layer, which handles business logic and repository interaction.
 *
 * @param cartService The service that contains cart-related business logic.
 */
class DeleteCartUseCase(private val cartService: CartService) {

    /**
     * Deletes a cart item by its unique cart ID.
     *
     * @param cartId The ID of the cart item to be deleted.
     * @return True if deletion was successful, false otherwise.
     */
    suspend operator fun invoke(cartId: String): Boolean {
        return cartService.deleteCart(cartId)
    }
}
