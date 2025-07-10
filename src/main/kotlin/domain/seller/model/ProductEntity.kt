package com.commerce.domain.seller.model

/**
 * Domain model representing a product created or managed by a seller.
 *
 * This model is used in the seller's context when creating, updating, or managing their products.
 *
 * @property sellerId The unique identifier of the seller who owns the product.
 * @property name The name of the product.
 * @property description A brief description of the product.
 * @property price The price of the product.
 * @property quantity The available stock quantity of the product.
 */
data class ProductEntity(
    val sellerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
