package com.commerce.domain.user.cart.service

import com.commerce.domain.user.cart.model.CartEntity
import com.commerce.domain.user.cart.model.CartUpdateEntity
import com.commerce.domain.user.cart.model.CartWithProductInfo

/**
 * Service interface for handling cart-related business logic.
 * Acts as a layer between the controller/use-case and repository.
 */
interface CartService {

    /**
     * Adds a product to the user's cart.
     *
     * @param cartEntity The cart entity containing user ID, product ID, and quantity.
     * @return The generated cart ID after insertion.
     */
    suspend fun addCart(cartEntity: CartEntity): String

    /**
     * Retrieves all cart items for a given user with full product details.
     *
     * @param userId The unique identifier of the user.
     * @return A list of cart items with their corresponding product information.
     */
    suspend fun findCartByUserId(userId: String): List<CartWithProductInfo>

    /**
     * Updates the quantity of a specific item in the user's cart.
     *
     * @param cartId The unique identifier of the cart item.
     * @param updateEntity Object containing updated quantity.
     * @return True if the update was successful, otherwise false.
     */
    suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean

    /**
     * Deletes a cart item from the user's cart.
     *
     * @param cartId The unique identifier of the cart item.
     * @return True if deletion was successful, otherwise false.
     */
    suspend fun deleteCart(cartId: String): Boolean
}
