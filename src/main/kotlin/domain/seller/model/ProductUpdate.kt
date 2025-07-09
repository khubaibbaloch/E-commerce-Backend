package com.commerce.domain.seller.model

data class ProductUpdate(
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
