package com.commerce.data.dto.product

import com.commerce.domain.models.product.ProductEntity

fun ProductRequest.toDomain(sellerId: String): ProductEntity {
    return ProductEntity(
        sellerId = sellerId,
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity
    )
}