package com.commerce.data.product.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val productId: String,
    val sellerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)