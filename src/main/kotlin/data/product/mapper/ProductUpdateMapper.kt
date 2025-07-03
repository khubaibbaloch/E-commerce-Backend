package com.commerce.data.product.mapper

import com.commerce.data.product.dto.ProductUpdateRequest
import com.commerce.domain.product.model.ProductUpdate

fun ProductUpdateRequest.toDomain(): ProductUpdate {
    return ProductUpdate(
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity
    )
}
