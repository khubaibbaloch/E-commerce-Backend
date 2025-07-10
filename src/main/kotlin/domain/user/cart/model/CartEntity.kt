package com.commerce.domain.user.cart.model

/**
 * Domain model representing a cart item in the system.
 *
 * @property userId The unique identifier of the user who owns the cart.
 * @property productId The unique identifier of the product added to the cart.
 * @property quantity The quantity of the product added to the cart.
 */
data class CartEntity(
    val userId: String,
    val productId: String,
    val quantity: Int,
)
