package com.commerce.data.dto.cart

import data.dto.cart.CartResponse
import com.commerce.domain.models.cart.CartWithProductInfo


fun CartWithProductInfo.toResponse(): CartResponse {
    return CartResponse(
        cartId = this.cartId,
        userId = this.userId,
        quantity = this.quantity,
        productId = this.product.productId,
        name = this.product.name,
        price = this.product.price
    )
}