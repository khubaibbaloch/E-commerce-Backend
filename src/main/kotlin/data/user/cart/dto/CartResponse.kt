package com.commerce.data.user.cart.dto

import kotlinx.serialization.Serializable

/**
 * Response DTO for cart items, returned when fetching the contents of a user's cart.
 */
@Serializable
data class CartResponse(

    // Unique identifier for the cart entry
    val cartId: String,

    // ID of the user who owns the cart
    val userId: String,

    // Quantity of the product in the cart
    val quantity: Int,

    // ID of the product added to the cart
    val productId: String,

    // Name of the product
    val name: String,

    // Price of a single unit of the product
    val price: Double
)
