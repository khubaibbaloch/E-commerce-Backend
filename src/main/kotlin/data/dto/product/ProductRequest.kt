package com.commerce.data.dto.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductRequest(
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
