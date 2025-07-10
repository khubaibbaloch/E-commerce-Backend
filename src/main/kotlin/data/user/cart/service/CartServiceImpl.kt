package com.commerce.data.user.cart.service

import com.commerce.domain.user.cart.model.CartEntity
import com.commerce.domain.user.cart.model.CartUpdateEntity
import com.commerce.domain.user.cart.model.CartWithProductInfo
import com.commerce.domain.user.cart.repository.CartRepository
import com.commerce.domain.user.cart.service.CartService

/**
 * Service implementation for managing user cart operations.
 * Delegates core logic to the CartRepository.
 */
class CartServiceImpl(private val cartRepository: CartRepository) : CartService {

    /**
     * Adds a new item to the user's cart.
     * Delegates to repository to perform DB insertion.
     *
     * @param cartEntity Object containing user ID, product ID, and quantity
     * @return Generated cart ID (UUID)
     */
    override suspend fun addCart(cartEntity: CartEntity): String {
        return cartRepository.addCart(cartEntity)
    }

    /**
     * Retrieves all items in the cart for a specific user, with product info.
     *
     * @param userId ID of the user whose cart is being retrieved
     * @return List of cart items with detailed product information
     */
    override suspend fun findCartByUserId(userId: String): List<CartWithProductInfo> {
        return cartRepository.findCartByUserId(userId)
    }

    /**
     * Updates the quantity of an item in the cart.
     *
     * @param cartId ID of the cart entry to update
     * @param updateEntity Contains new quantity
     * @return True if update was successful, false otherwise
     */
    override suspend fun updateCart(cartId: String, updateEntity: CartUpdateEntity): Boolean {
        return cartRepository.updateCart(cartId, updateEntity)
    }

    /**
     * Deletes an item from the cart by its ID.
     *
     * @param cartId ID of the cart entry to delete
     * @return True if deletion was successful, false otherwise
     */
    override suspend fun deleteCart(cartId: String): Boolean {
        return cartRepository.deleteCart(cartId)
    }
}
