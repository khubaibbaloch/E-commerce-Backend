package com.commerce.domain.product.model

data class ProductUpdate(
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
