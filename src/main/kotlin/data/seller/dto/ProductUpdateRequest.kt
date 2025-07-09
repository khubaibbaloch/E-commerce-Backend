package com.commerce.data.seller.dto

import kotlinx.serialization.Serializable

/**
 * DTO for updating product details.
 * Sent by the seller when modifying an existing product.
 */
@Serializable
class ProductUpdateRequest(
    val name: String,           // Updated name/title of the product
    val description: String,    // Updated description/details about the product
    val price: Double,          // Updated selling price
    val quantity: Int           // Updated available stock quantity
)
