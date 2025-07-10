package com.commerce.domain.admin.model

/**
 * Domain entity representing a product with full admin-level visibility.
 * Used by admin services to access and manage all products in the system.
 */
data class ProductAdminEntity(
    val id: Int,                   // Auto-incremented database ID (internal reference)
    val productId: String,         // Unique product UUID for public/system-level identification
    val sellerId: String,          // The ID of the seller who listed this product
    val name: String,              // Name/title of the product
    val description: String,       // Detailed product description
    val price: Double,             // Price of the product
    val quantity: Int              // Available quantity in stock
)
