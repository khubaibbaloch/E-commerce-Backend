package com.commerce.data.user.product.mapper

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.data.product.dto.ProductResponse

fun ProductEntityWithId.toResponse(): ProductResponse {
    return ProductResponse(
        productId = this.productId,
        sellerId = this.sellerId,
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity
    )
}
