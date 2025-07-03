package com.commerce.data.cart.dto


import kotlinx.serialization.Serializable

@Serializable
data class CartWithProductInfo(
    val cartId: String,
    val userId: String,
    val quantity: Int,
    val product: ProductEntityWithId
)