package com.commerce.domain.cart.model

data class CartEntity(
    val userId: String,
    val productId: String,
    val quantity: Int,
)