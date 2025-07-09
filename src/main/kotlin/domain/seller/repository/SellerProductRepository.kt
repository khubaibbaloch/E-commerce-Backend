package com.commerce.domain.seller.repository

import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.model.ProductUpdate

interface SellerProductRepository {
    suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean
    suspend fun deleteProductById(productId: String): Boolean
    suspend fun insertProduct(productEntity: ProductEntity): String
}