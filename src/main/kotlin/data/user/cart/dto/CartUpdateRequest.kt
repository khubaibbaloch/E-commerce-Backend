package com.commerce.data.user.cart.dto

import kotlinx.serialization.Serializable

/**
 * Request DTO for updating the quantity of a product in the user's cart.
 */
@Serializable
data class CartUpdateRequest(

    // The new quantity of the product to be set in the cart
    val quantity: Int
)
