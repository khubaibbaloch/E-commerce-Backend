package com.commerce.data.cart.mapper

import com.commerce.data.cart.dto.CartRequest
import com.commerce.domain.cart.model.CartEntity

fun CartRequest.toDomain(userId:String): CartEntity {
    return CartEntity(
        userId = userId,
        productId = this.productId,
        quantity = this.quantity
    )
}