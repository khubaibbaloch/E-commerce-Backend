package com.commerce.domain.seller.model

/**
 * Domain model representing the fields that can be updated for a product.
 *
 * This is typically used when a seller wants to modify an existing product's details.
 *
 * @property name The new or updated name of the product.
 * @property description The new or updated description of the product.
 * @property price The new price of the product.
 * @property quantity The updated available stock quantity.
 */
data class ProductUpdate(
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)
