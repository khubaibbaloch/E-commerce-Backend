package com.commerce.data.seller.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object (DTO) for product creation or update by sellers.
 */
@Serializable
data class ProductRequest(
    val name: String,           // Name/title of the product
    val description: String,    // Brief description/details about the product
    val price: Double,          // Selling price of the product (in desired currency)
    val quantity: Int           // Available stock quantity
)
