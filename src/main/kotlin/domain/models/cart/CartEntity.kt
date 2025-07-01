package com.commerce.domain.models.cart

data class CartEntity(
    val userId: String,
    val productId: String,
    val quantity: Int,
)