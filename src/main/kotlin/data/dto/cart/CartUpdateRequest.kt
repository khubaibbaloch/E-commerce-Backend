package com.commerce.data.dto.cart

import kotlinx.serialization.Serializable

@Serializable
data class CartUpdateRequest(val quantity: Int)