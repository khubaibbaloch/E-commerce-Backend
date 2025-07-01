package data.dto.cart

import kotlinx.serialization.Serializable

@Serializable
data class ProductEntityWithId(
    val productId : String,
    val sellerId: String,
    val name: String,
    val description: String,
    val price: Double,
    val quantity: Int
)