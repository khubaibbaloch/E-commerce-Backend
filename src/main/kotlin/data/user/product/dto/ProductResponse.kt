package com.commerce.data.user.product.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) used to send product information to the client.
 */
@Serializable
data class ProductResponse(
    val productId: String,      // Unique identifier for the product (UUID or custom ID)
    val sellerId: String,       // Identifier of the seller who owns the product
    val name: String,           // Name/title of the product
    val description: String,    // Short description or details of the product
    val price: Double,          // Price of the product
    val quantity: Int           // Available stock quantity of the product
)
