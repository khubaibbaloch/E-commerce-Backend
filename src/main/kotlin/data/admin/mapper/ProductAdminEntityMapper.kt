package com.commerce.data.admin.mapper

import com.commerce.domain.admin.model.ProductAdminEntity
import data.admin.dto.ProductAdminResponse

/**
 * Maps a domain-level ProductAdminEntity to a DTO suitable for admin responses.
 */
fun ProductAdminEntity.toResponse(): ProductAdminResponse {
    return ProductAdminResponse(
        id = this.id,                   // Internal DB ID for reference (auto-increment)
        productId = this.productId,     // Public product UUID or unique identifier
        sellerId = this.sellerId,       // ID of the seller who owns the product
        name = this.name,               // Product name
        description = this.description, // Brief product description
        price = this.price,             // Product price
        quantity = this.quantity        // Available product stock
    )
}
