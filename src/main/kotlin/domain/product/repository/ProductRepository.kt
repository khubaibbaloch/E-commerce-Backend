package com.commerce.domain.product.repository

import com.commerce.domain.product.model.ProductEntity
import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.product.model.ProductUpdate

interface ProductRepository {
    suspend fun findProductsByName(productName: String): List<ProductEntityWithId>
    suspend fun getAllProducts(): List<ProductEntityWithId>
    suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean
    suspend fun deleteProductById(productId: String): Boolean
    suspend fun insertProduct(productEntity: ProductEntity): String
}