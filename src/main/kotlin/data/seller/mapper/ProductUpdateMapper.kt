package com.commerce.data.seller.mapper

import com.commerce.data.seller.dto.ProductUpdateRequest
import com.commerce.domain.seller.model.ProductUpdate

/**
 * Extension function to convert a ProductUpdateRequest DTO to a domain-level ProductUpdate model.
 *
 * @return A ProductUpdate domain model used for updating product information.
 */
fun ProductUpdateRequest.toDomain(): ProductUpdate {
    return ProductUpdate(
        name = this.name,               // Updated product name
        description = this.description, // Updated product description
        price = this.price,             // Updated product price
        quantity = this.quantity        // Updated product quantity
    )
}
