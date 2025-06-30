package data.dto.product

import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val sellerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)