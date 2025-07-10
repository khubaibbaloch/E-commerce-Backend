package com.commerce.data.user.cart.mapper

import com.commerce.data.user.cart.dto.CartResponse
import com.commerce.domain.user.cart.model.CartWithProductInfo

/**
 * Extension function to map domain-level CartWithProductInfo
 * into a simplified CartResponse DTO for API exposure.
 */
fun CartWithProductInfo.toResponse(): CartResponse {
    return CartResponse(
        cartId = this.cartId,         // Unique identifier for the cart entry
        userId = this.userId,         // ID of the user who owns the cart
        quantity = this.quantity,     // Quantity of the product in the cart
        productId = this.product.productId, // Product ID added to the cart
        name = this.product.name,     // Product name
        price = this.product.price    // Price of the product at time of addition
    )
}
