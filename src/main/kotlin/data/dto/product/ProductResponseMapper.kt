package com.commerce.data.dto.product

import data.dto.product.ProductResponse
import domain.models.ProductEntity

fun ProductEntity.toResponse(): ProductResponse {
    return ProductResponse(
        sellerId = this.sellerId,
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity
    )
}