package com.commerce.data.seller.mapper

import com.commerce.data.seller.dto.ProductRequest
import com.commerce.domain.seller.model.ProductEntity

/**
 * Extension function to map a ProductRequest DTO to a ProductEntity domain model.
 *
 * @param sellerId The unique identifier of the seller creating the product.
 * @return A domain-level ProductEntity for business logic or persistence.
 */
fun ProductRequest.toDomain(sellerId: String): ProductEntity {
    return ProductEntity(
        sellerId = sellerId,           // Seller ID associated with the product
        name = this.name,              // Product name from the request
        description = this.description,// Product description from the request
        price = this.price,            // Product price from the request
        quantity = this.quantity       // Product quantity from the request
    )
}
