package com.commerce.data.dto.product

import domain.models.ProductEntity

fun ProductRequest.toDomain(sellerId: String): ProductEntity {
    return ProductEntity(
        sellerId = sellerId,
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity
    )
}