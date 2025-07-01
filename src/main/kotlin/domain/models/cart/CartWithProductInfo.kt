package com.commerce.domain.models.cart

import com.commerce.domain.models.product.ProductEntityWithId

data class CartWithProductInfo(
    val cartId: String,
    val userId: String,
    val quantity: Int,
    val product: ProductEntityWithId
)
