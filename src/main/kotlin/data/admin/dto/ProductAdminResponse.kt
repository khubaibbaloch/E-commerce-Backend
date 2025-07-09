package data.admin.dto

import kotlinx.serialization.Serializable

/**
 * DTO representing product details visible to Admins.
 * Admins can view product along with its internal DB ID and seller association.
 */
@Serializable
data class ProductAdminResponse(
    val id: Int,              // Internal auto-increment ID from the database (primary key)
    val productId: String,    // UUID or external identifier for the product
    val sellerId: String,     // ID of the seller who owns the product
    val name: String,         // Product name
    val description: String,  // Brief description of the product
    val price: Double,        // Price of the product in currency units
    val quantity: Int         // Available stock quantity
)
