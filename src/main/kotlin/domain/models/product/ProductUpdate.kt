package com.commerce.domain.models.product

data class ProductUpdate(
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
