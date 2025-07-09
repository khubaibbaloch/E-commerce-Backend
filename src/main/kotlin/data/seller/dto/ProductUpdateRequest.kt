package com.commerce.data.product.dto

import kotlinx.serialization.Serializable

@Serializable
class ProductUpdateRequest(
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
