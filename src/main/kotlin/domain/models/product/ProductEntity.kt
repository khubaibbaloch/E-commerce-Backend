package com.commerce.domain.models.product

data class ProductEntity(
    val sellerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
