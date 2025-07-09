package com.commerce.domain.cart.model

import com.commerce.domain.product.model.ProductEntityWithId

data class CartWithProductInfo(
    val cartId: String,
    val userId: String,
    val quantity: Int,
    val product: ProductEntityWithId
)
