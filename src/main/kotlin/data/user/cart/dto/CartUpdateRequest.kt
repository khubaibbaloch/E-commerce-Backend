package com.commerce.data.cart.dto

import kotlinx.serialization.Serializable

@Serializable
data class CartUpdateRequest(val quantity: Int)