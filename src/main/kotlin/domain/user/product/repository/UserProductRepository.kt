package com.commerce.domain.user.product.repository

import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.seller.model.ProductUpdate

interface UserProductRepository {
    suspend fun findProductsByName(productName: String): List<ProductEntityWithId>
    suspend fun getAllProducts(): List<ProductEntityWithId>
}