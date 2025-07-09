package com.commerce.data.product.mapper

import com.commerce.data.product.dto.ProductRequest
import com.commerce.domain.seller.model.ProductEntity

fun ProductRequest.toDomain(sellerId: String): ProductEntity {
    return ProductEntity(
        sellerId = sellerId,
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity
    )
}