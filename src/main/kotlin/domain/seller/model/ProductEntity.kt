package com.commerce.domain.seller.model

data class ProductEntity(
    val sellerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
