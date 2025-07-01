package com.commerce.data.dto.cart

import com.commerce.domain.models.cart.CartEntity

fun CartRequest.toDomain(userId:String): CartEntity {
    return CartEntity(
        userId = userId,
        productId = this.productId,
        quantity = this.quantity
    )
}