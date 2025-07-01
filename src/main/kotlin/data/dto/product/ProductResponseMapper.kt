package com.commerce.data.dto.product

import com.commerce.domain.models.product.ProductEntityWithId
import data.dto.product.ProductResponse

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
