package com.commerce.domain.user.cart.usecase

import com.commerce.domain.user.cart.model.CartWithProductInfo
import com.commerce.domain.user.cart.service.CartService

/**
 * Use case for retrieving all cart items associated with a specific user.
 *
 * This use case delegates the call to the CartService layer, which fetches
 * the cart items along with their associated product information.
 *
 * @param cartService The service layer that provides cart-related operations.
 */
class FindCartByUserIdUseCase(private val cartService: CartService) {

    /**
     * Invokes the use case to get a list of cart items for the provided user ID.
     *
     * @param userId The ID of the user whose cart items are to be retrieved.
     * @return A list of [CartWithProductInfo] containing product and cart details.
     */
    suspend operator fun invoke(userId: String): List<CartWithProductInfo> {
        return cartService.findCartByUserId(userId = userId)
    }
}
