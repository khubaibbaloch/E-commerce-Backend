package com.commerce.data.cart.mapper

import com.commerce.data.cart.dto.CartResponse
import com.commerce.domain.cart.model.CartWithProductInfo


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