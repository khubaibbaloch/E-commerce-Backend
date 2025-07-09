package data.admin.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductAdminResponse(
    val id : Int,
    val productId : String,
    val sellerId : String,
    val name : String,
    val description : String,
    val price : Double,
    val quantity : Int
)
