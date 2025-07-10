package com.commerce.data.user.cart.dto

import kotlinx.serialization.Serializable

/**
 * DTO representing a cart item along with detailed product information.
 */
@Serializable
data class CartWithProductInfo(

    // Unique identifier of the cart entry
    val cartId: String,

    // The user to whom this cart item belongs
    val userId: String,

    // Quantity of the product in the cart
    val quantity: Int,

    // Associated product details (id, name, description, etc.)
    val product: ProductEntityWithId
)
