package com.commerce.domain.product.service

import com.commerce.domain.product.model.ProductEntity
import com.commerce.domain.product.model.ProductEntityWithId
import com.commerce.domain.product.model.ProductUpdate

interface ProductService {
    suspend fun insertProduct(productEntity: ProductEntity): String
    suspend fun findProductByName(productName: String): List<ProductEntityWithId>
    suspend fun getAllProducts(): List<ProductEntityWithId>
    suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean
    suspend fun deleteProductById(productId: String): Boolean
}