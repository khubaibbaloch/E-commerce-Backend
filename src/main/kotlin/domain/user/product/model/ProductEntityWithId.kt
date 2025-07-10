package com.commerce.domain.user.product.model

/**
 * Domain model representing a product along with its unique identifier.
 *
 * @property productId Unique identifier of the product.
 * @property sellerId Identifier of the seller who owns the product.
 * @property name Name of the product.
 * @property description Description of the product.
 * @property price Price of the product.
 * @property quantity Available quantity of the product in stock.
 */
data class ProductEntityWithId(
    val productId: String,
    val sellerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
