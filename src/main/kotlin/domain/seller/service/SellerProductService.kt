package com.commerce.domain.seller.service

import com.commerce.domain.seller.model.ProductEntity
import com.commerce.domain.seller.model.ProductUpdate

interface SellerProductService {
    suspend fun updateProductById(productId: String, updatedProduct: ProductUpdate): Boolean
    suspend fun deleteProductById(productId: String): Boolean
    suspend fun insertProduct(productEntity: ProductEntity): String
}