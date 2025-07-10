package com.commerce.data.user.cart.dto

import kotlinx.serialization.Serializable

/**
 * Request DTO for adding or updating a product in the user's cart.
 */
@Serializable
data class CartRequest(

    // ID of the product to add to the cart (usually a UUID)
    val productId: String,

    // Quantity of the product the user wants to add
    val quantity: Int
)
