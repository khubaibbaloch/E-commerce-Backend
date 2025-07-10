package com.commerce.domain.user.cart.repository

import com.commerce.domain.user.cart.model.CartEntity
import com.commerce.domain.user.cart.model.CartUpdateEntity
import com.commerce.domain.user.cart.model.CartWithProductInfo

/**
 * Repository interface for cart-related database operations.
 * Defines the contract for adding, retrieving, updating, and deleting cart items.
 */
interface CartRepository {

    /**
     * Adds a new item to the user's cart.
     *
     * @param cartEntity The cart entity containing user ID, product ID, and quantity.
     * @return The generated cart ID as a String.
     */
    suspend fun addCart(cartEntity: CartEntity): String

    /**
     * Retrieves all cart items for a specific user, including product details.
     *
     * @param userId The ID of the user.
     * @return A list of cart items with associated product information.
     */
    suspend fun findCartByUserId(userId: String): List<CartWithProductInfo>

    /**
     * Updates the quantity of a specific item in the cart.
     *
     * @param cartId The ID of the cart item to update.
     * @param updateEntity Contains the new quantity value.
     * @return True if the update was successful, false otherwise.
     */
    suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean

    /**
     * Deletes a specific item from the cart.
     *
     * @param cartId The ID of the cart item to delete.
     * @return True if the deletion was successful, false otherwise.
     */
    suspend fun deleteCart(cartId: String): Boolean
}
