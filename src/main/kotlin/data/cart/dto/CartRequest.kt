package com.commerce.data.cart.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartRequest(
    val productId:String,
    val quantity:Int
)