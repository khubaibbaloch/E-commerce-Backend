package com.commerce.data.dto.cart

import kotlinx.serialization.Serializable

@Serializable
data class CartRequest(
    val productId:String,
    val quantity:Int
)