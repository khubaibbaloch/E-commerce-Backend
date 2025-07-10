package com.commerce.domain.user.cart.usecase

import com.commerce.domain.user.cart.model.CartEntity
import com.commerce.domain.user.cart.service.CartService

/**
 * Use case for adding a product to the user's cart.
 * Delegates the operation to the CartService.
 *
 * @param cartService The service responsible for cart business logic.
 */
class AddCartUseCase(private val cartService: CartService) {

    /**
     * Adds the given cart item by calling the CartService.
     *
     * @param cartEntity The cart entity containing productId, userId, and quantity.
     * @return The generated cart ID as a String.
     */
    suspend operator fun invoke(cartEntity: CartEntity): String {
        return cartService.addCart(cartEntity)
    }
}
