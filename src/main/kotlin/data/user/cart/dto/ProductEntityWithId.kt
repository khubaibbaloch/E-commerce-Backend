package com.commerce.data.user.cart.dto

import kotlinx.serialization.Serializable

/**
 * DTO representing product details with its unique ID.
 * Typically used in responses where product identity and ownership are relevant (e.g., cart, order history).
 */
@Serializable
data class ProductEntityWithId(

    // Unique identifier for the product
    val productId: String,

    // Seller's unique identifier who owns the product
    val sellerId: String,

    // Name/title of the product
    val name: String,

    // Brief description of the product
    val description: String,

    // Price of the product in desired currency
    val price: Double,

    // Available stock quantity
    val quantity: Int
)
