package com.commerce.data.admin.mapper

import com.commerce.domain.admin.model.ProductAdminEntity
import data.admin.dto.ProductAdminResponse

fun ProductAdminEntity.toResponse():ProductAdminResponse {
    return ProductAdminResponse(
        id = this.id,
        productId = this.productId,
        sellerId = this.sellerId,
        name = this.name,
        description = this.description,
        price = this.price,
        quantity = this.quantity
    )
}
