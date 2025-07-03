package com.commerce.data.cart.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    val cartId: String,
    val userId: String,
    val quantity: Int,
    val productId: String,
    val name: String,
    val price: Double
)