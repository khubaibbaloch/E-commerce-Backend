package com.commerce.domain.admin.model

data class ProductAdminEntity(
    val id : Int,
    val productId : String,
    val sellerId : String,
    val name : String,
    val description : String,
    val price : Double,
    val quantity : Int
)
