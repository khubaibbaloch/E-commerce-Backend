package com.commerce.data.user.product.mapper

import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.data.user.product.dto.ProductResponse

/**
 * Extension function to map a domain-level ProductEntityWithId object
 * to a DTO-level ProductResponse object for client consumption.
 */
fun ProductEntityWithId.toResponse(): ProductResponse {
    return ProductResponse(
        productId = this.productId,       // Map product unique ID
        sellerId = this.sellerId,         // Map seller ID who owns the product
        name = this.name,                 // Map product name/title
        description = this.description,   // Map product description/details
        price = this.price,               // Map product price
        quantity = this.quantity          // Map product stock quantity
    )
}
