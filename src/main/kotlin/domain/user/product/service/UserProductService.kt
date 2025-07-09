package com.commerce.domain.user.product.service

import com.commerce.domain.product.model.ProductEntityWithId

interface UserProductService {
    suspend fun findProductByName(productName: String): List<ProductEntityWithId>
    suspend fun getAllProducts(): List<ProductEntityWithId>
}