package com.commerce.data.dto.product

import com.commerce.domain.models.product.ProductUpdate

fun ProductUpdateRequest.toDomain(): ProductUpdate {
    return ProductUpdate(
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity
    )
}
